package com.example.copyritersShop.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.copyritersShop.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        operator_register_button.setOnClickListener { startRegistrationActivity("operator") }
        copyriter_register_button.setOnClickListener { startRegistrationActivity("copyriter") }
        client_register_button.setOnClickListener { startRegistrationActivity("client") }

        operator_login_button.setOnClickListener { startLoginActivity("operator") }
        copyriter_login_button.setOnClickListener { startLoginActivity("copyriter") }
        client_login_button.setOnClickListener { startLoginActivity("client") }
    }

    private fun startRegistrationActivity(tag: String) {
        startActivity(Intent(this, RegistrationActivity::class.java).putExtra("tag", tag))
    }

    private fun startLoginActivity(tag: String) {
        startActivity(Intent(this, LoginActivity::class.java).putExtra("tag", tag))
    }
}
