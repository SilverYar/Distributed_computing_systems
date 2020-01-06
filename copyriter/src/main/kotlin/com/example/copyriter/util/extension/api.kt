@file:Suppress("FunctionName")

package com.example.copyriter.util.extension

data class Error(val error: String)

fun ElementAlreadyExists() = Error("Element already exists")
fun ElementNotFound() = Error("Element not found")