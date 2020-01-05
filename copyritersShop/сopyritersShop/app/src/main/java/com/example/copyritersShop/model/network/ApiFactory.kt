package com.example.copyritersShop.model.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.copyritersShop.model.Config

object ApiFactory {

    private var mClient: OkHttpClient? = null
    private var mService: ApiService? = null

    val apiService: ApiService
        get() {
            var service = mService
            if (mService == null) {
                synchronized(ApiFactory::class.java) {
                    service = mService
                    if (service == null) {
                        mService = createService()
                        service = mService
                    }
                }
            }
            return service!!
        }

    private val gsonConverterFactory: GsonConverterFactory
        get() {
            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
            return GsonConverterFactory.create(gson)
        }

    private val client: OkHttpClient
        get() {
            var client = mClient
            if (client == null) {
                synchronized(ApiFactory::class.java) {
                    client = mClient
                    if (client == null) {
                        mClient = buildClient()
                        client = mClient
                    }
                }
            }
            return client!!
        }

    private fun createService() =
            Retrofit.Builder()
                    .baseUrl(Config.apiUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(client)
                    .build()
                    .create(ApiService::class.java)

    @JvmOverloads
    fun recreateApiService(resetHttpClient: Boolean = false) {
        if (resetHttpClient) mClient = null
        mService = null
        mService = apiService
    }

    private fun buildClient() =
            OkHttpClient.Builder()
                    .addInterceptor(LoggingInterceptor(ApiFactory::class))
                    .build()

}
