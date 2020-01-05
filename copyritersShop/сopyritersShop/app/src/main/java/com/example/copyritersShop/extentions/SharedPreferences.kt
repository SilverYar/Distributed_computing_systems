package com.example.copyritersShop.extentions

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.reflect.KClass

fun SharedPreferences.autoEdit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

fun SharedPreferences.put(key: String, value: String?) =
        autoEdit { putString(key, value) }

fun SharedPreferences.get(key: String, default: String? = null): String? =
        getString(key, default)

fun SharedPreferences.put(key: String, value: Boolean) =
        autoEdit { putBoolean(key, value) }

fun SharedPreferences.get(key: String, default: Boolean = false): Boolean =
        getBoolean(key, default)

fun SharedPreferences.put(key: String, value: Int) =
        autoEdit { putInt(key, value) }

fun SharedPreferences.get(key: String, default: Int = 0): Int =
        getInt(key, default)

fun SharedPreferences.put(key: String, value: Long) =
        autoEdit { putLong(key, value) }

fun SharedPreferences.get(key: String, default: Long = 0): Long =
        getLong(key, default)

fun <T> SharedPreferences.put(key: String, obj: T) =
        put(key, Gson().toJson(obj))

fun <T : Any> SharedPreferences.get(type: KClass<T>, key: String, default: T? = null): T? =
        Gson().fromJson(getString(key, null), type.java) ?: default