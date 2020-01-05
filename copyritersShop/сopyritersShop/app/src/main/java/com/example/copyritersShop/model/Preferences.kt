package com.example.copyritersShop.model

import android.content.Context
import android.content.SharedPreferences
import com.example.copyritersShop.extentions.get
import com.example.copyritersShop.extentions.put
import com.example.copyritersShop.model.entity.User
import com.example.copyritersShop.model.item.UserType
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

object Preferences {
    private const val APP_PREFERENCES = "APP_PREFERENCES"

    private lateinit var sharedPrefs: SharedPreferences
    fun initialize(context: Context) {
        sharedPrefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                ?: throw IllegalStateException("Preferences initialization failed")
    }

    // Preferences list here

    var serverHost by StringPref("server_host")
    var serverPort by IntPref("server_port", -1)
    var user by JsonPref(User::class, "user")
    var userType by JsonPref(UserType::class, "user_type")

    // End preferences list

    private class StringPref(val key: String, val default: String? = null) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = sharedPrefs.get(key, default)
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) = sharedPrefs.put(key, value)
    }

    private class BooleanPref(val key: String, val default: Boolean = false) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = sharedPrefs.get(key, default)
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) = sharedPrefs.put(key, value)
    }

    private class IntPref(val key: String, val default: Int = 0) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = sharedPrefs.get(key, default)
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) = sharedPrefs.put(key, value)
    }

    private class LongPref(val key: String, val default: Long = 0) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = sharedPrefs.get(key, default)
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) = sharedPrefs.put(key, value)
    }

    private class JsonPref<T : Any>(val type: KClass<T>, val key: String, val default: T? = null) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = sharedPrefs.get(type, key, default)
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) = sharedPrefs.put(key, value)
    }
}