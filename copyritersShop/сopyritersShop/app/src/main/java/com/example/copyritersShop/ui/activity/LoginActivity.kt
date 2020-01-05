package com.example.copyritersShop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.copyritersShop.R
import com.example.copyritersShop.model.Preferences
import com.example.copyritersShop.model.entity.User
import com.example.copyritersShop.model.item.UserType
import com.example.copyritersShop.model.item.exceptionHandler
import com.example.copyritersShop.model.repository.AuthRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }


    private lateinit var tag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tag = intent.getStringExtra("tag") ?: ""
        button.setOnClickListener {
            launch {
                try {
                    val login = login_edit.text.toString()
                    val password = password_edit.text.toString()
                    val userType = when (tag) {
                        "client" -> UserType.CLIENT
                        "operator" -> UserType.OPERATOR
                        "copyriter" -> UserType.COPYRITER
                        else -> throw IllegalArgumentException()
                    }
                    val result = withContext(Dispatchers.IO) { AuthRepository().loginUser(userType, login, password) }
                    Preferences.user = result
                    Preferences.userType = userType
                    startMainActivity(userType, result)
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Что-то не так", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startMainActivity(type: UserType, user: User) {
        when (type) {
            UserType.CLIENT -> startActivity(Intent(this, ClientMainActivity::class.java))
            UserType.OPERATOR -> startActivity(Intent(this, OperatorMainActivity::class.java))
            UserType.COPYRITER -> startActivity(Intent(this,  CopyriterMainActivity::class.java).putExtra("copyriter_id", user.id))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
