package com.example.copyritersShop.model.item

class FieldNotFoundException(fieldName: String) : NoSuchElementException("Required field: '$fieldName'")

class AuthorizationFailException : Exception()

class ResponseFailException : Exception()

class ServerErrorMessage(val error: String) : Exception()
