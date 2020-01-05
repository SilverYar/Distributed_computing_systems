package com.example.copyritersShop.model

import android.content.Context
import android.util.Log
import java.util.*
import kotlin.reflect.KClass

object Logger {

    private var appContextName: String? = null

    fun initialize(context: Context) {
        appContextName = context.javaClass.name
    }

    fun i(context: Context, msg: String) = i(context.javaClass.simpleName, msg)

    fun i(context: KClass<*>, msg: String) = i(context.simpleName, msg)

    fun i(className: String?, msg: String) {
        val tag = getTag(className)
        Log.d(tag, msg)
    }

    fun e(e: Throwable) = e(e.javaClass.name, Arrays.toString(e.stackTrace))

    fun e(context: Context, msg: String) = e(context.javaClass.name, msg)

    fun e(context: KClass<*>, msg: String) = e(context.simpleName, msg)

    fun e(className: String?, msg: String) {
        val tag = getTag(className)
        Log.e(tag, msg)
    }

    private fun getTag(className: String?): String? {
        return className ?: appContextName
    }
}

