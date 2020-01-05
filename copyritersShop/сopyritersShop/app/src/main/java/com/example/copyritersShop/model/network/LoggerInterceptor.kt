package com.example.copyritersShop.model.network

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import com.example.copyritersShop.BuildConfig
import kotlin.reflect.KClass

fun LoggingInterceptor(context: KClass<*>) =
        HttpLoggingInterceptor { message ->
            Log.i(context.simpleName, message)
        }.apply {
            level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.BASIC
        }