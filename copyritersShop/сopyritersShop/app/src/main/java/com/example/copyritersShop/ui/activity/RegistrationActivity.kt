package com.example.copyritersShop.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.copyritersShop.R
import com.example.copyritersShop.model.entity.User
import com.example.copyritersShop.model.item.UserType
import com.example.copyritersShop.model.item.exceptionHandler
import com.example.copyritersShop.model.repository.AuthRepository
import kotlinx.android.synthetic.main.activity_registartion.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegistrationActivity : AppCompatActivity(), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }

    private lateinit var tag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registartion)
        tag = intent.getStringExtra("tag") ?: ""
        button.setOnClickListener {

            launch {
                val name = name_edit.text.toString()
                val login = login_edit.text.toString()
                val password = password_edit.text.toString()
                try {
                    val userType = when (tag) {
                        "client" -> UserType.CLIENT
                        "operator" -> UserType.OPERATOR
                        "copyriter" -> UserType.COPYRITER
                        else -> throw IllegalArgumentException()
                    }
                    withContext(Dispatchers.IO) { AuthRepository().registerUser(userType, User(name = name, login = login, password = password)) }
                    onBackPressed()
                    Toast.makeText(this@RegistrationActivity, "Пользователь создан", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@RegistrationActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
