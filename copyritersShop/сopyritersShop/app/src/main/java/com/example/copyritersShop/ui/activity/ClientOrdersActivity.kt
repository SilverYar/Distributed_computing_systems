package com.example.copyritersShop.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.copyritersShop.R
import com.example.copyritersShop.extentions.str
import com.example.copyritersShop.model.entity.Order
import com.example.copyritersShop.model.entity.User
import com.example.copyritersShop.model.item.OrderStatus
import com.example.copyritersShop.model.item.exceptionHandler
import com.example.copyritersShop.model.repository.ClientsRepository
import com.example.copyritersShop.model.repository.OrdersRepository
import com.example.copyritersShop.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.activity_client_orders.*
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class ClientOrdersActivity : AppCompatActivity(), OrderAdapter.OnItemClickListener, CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }


    override fun onItemClick(item: Order) {
        showStatusSelectionDialog(item.status) { newStatus ->
            launch {
                try {
                    item.status = newStatus
                    withContext(Dispatchers.IO) { OrdersRepository().updateOrder(item) }
                    updateOrders()
                } catch (e: Exception) {
                    Toast.makeText(this@ClientOrdersActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private var client: User? = null
    private val adapter = OrderAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_orders)
        title = "Список заказов"
        recycler.adapter = adapter
        updateOrders()
    }

    private fun updateOrders() {
        launch {
            try {
                val orders = withContext(Dispatchers.IO) {
                    OrdersRepository().getAllOrders()
                }
                adapter.items.clear()
                adapter.items.addAll(orders)
                adapter.notifyDataSetChanged()
            } catch (e :Exception) {
                Toast.makeText(this@ClientOrdersActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showStatusSelectionDialog(currentStatus: OrderStatus, block: (newStatus: OrderStatus) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Изменение статуса заказа")

        val statuses = when (currentStatus) {
            else -> {
                Toast.makeText(this, "Смена статуса недоступна", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}