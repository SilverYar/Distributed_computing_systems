package com.example.copyritersShop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.copyritersShop.R
import com.example.copyritersShop.model.Preferences
import com.example.copyritersShop.model.entity.Order
import com.example.copyritersShop.model.item.exceptionHandler
import com.example.copyritersShop.model.repository.OrdersRepository
import kotlinx.android.synthetic.main.activity_client_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class ClientMainActivity : AppCompatActivity(), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }

    private var products = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)
        title = "Создание заказа"
        generate_button.setOnClickListener {
            products = ArrayList(Menu.generateOrder())
            order_text.text = products.joinToString(separator = "\n")
        }
        create_button.setOnClickListener {
            launch {
                try {
                    withContext(Dispatchers.IO) {
                        OrdersRepository().createOrder(Order(
                                products = this@ClientMainActivity.products,
                                clientId = Preferences.user?.id ?: throw IllegalStateException()
                        ))
                    }
                    Toast.makeText(this@ClientMainActivity, "Заказ оформлен", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ClientMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }

        show_button.setOnClickListener {
            startActivity(Intent(this, ClientOrdersActivity::class.java))
        }
    }

    object Menu {
        val items = mutableListOf<String>()

        init {
            create("Политическая новось на заказ", 100000)
            create("Экономическая новость на заказ", 20000)
            create("Техническая статья", 200000)
            create("Гуманитарная статья", 100000)
            create("Книга жанра фантастика", 35000)
            create("Книга жанра детектив", 50000)
            create("Технический текст", 30000)
            create("Экономический текст", 25000)
        }

        private fun create(name: String, price: Int) = items.add("$name - $price")

        fun generateOrder(): MutableList<String> {
            val count = Random.nextInt(2) + 2
            return MutableList(count) { items.random() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
