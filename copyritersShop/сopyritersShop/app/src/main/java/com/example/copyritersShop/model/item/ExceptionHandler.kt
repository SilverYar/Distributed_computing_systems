package com.example.copyritersShop.model.item

import kotlinx.coroutines.CoroutineExceptionHandler
import com.example.copyritersShop.model.Logger

val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Logger.e(throwable) }

fun exceptionHandler(block: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    Logger.e(throwable)
    block()
}