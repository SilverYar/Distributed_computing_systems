package com.example.copyritersShop.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.copyritersShop.R
import com.example.copyritersShop.model.Preferences
import com.example.copyritersShop.model.network.ApiFactory
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        button.setOnClickListener {
            val (newHost, newPort) = edit_text.text.split(':')
            Preferences.serverHost = newHost
            Preferences.serverPort = newPort.toInt()
            ApiFactory.recreateApiService()
            startActivity(Intent(this, StartActivity::class.java))
        }
        setupHostAndPort()
    }

    @SuppressLint("SetTextI18n")
    private fun setupHostAndPort() {
        val host = Preferences.serverHost ?: return
        val port = Preferences.serverPort
        edit_text.setText("$host:${ if (port == -1) "" else port.toString() }")
    }
}
