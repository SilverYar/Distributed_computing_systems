package com.example.copyritersShop

import android.app.Application
import com.example.copyritersShop.model.Preferences

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Preferences.initialize(this)
    }
}