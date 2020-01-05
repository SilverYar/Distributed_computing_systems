package com.example.copyritersShop.model

object Config {

    val apiUrl: String
        get() = "http://${Preferences.serverHost}:${Preferences.serverPort}/api/"
}