package com.example.copyritersShop.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.copyritersShop.R
import com.example.copyritersShop.extentions.str
import com.example.copyritersShop.model.entity.Order
import com.example.copyritersShop.model.item.OrderStatus
import com.example.copyritersShop.model.item.exceptionHandler
import com.example.copyritersShop.model.repository.OrdersRepository
import com.example.copyritersShop.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.activity_operator_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class OperatorMainActivity : AppCompatActivity(), OrderAdapter.OnItemClickListener, CoroutineScope {


    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }

    override fun onItemClick(item: Order) {
        showStatusSelectionDialog { newStatus ->
            launch {
                try {
                    item.status = newStatus
                    withContext(Dispatchers.IO) { OrdersRepository().updateOrder(item) }
                    updateOrders()
                } catch (e: Exception) {
                    Toast.makeText(this@OperatorMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val adapter = OrderAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator_main)
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
            } catch (e: Exception) {
                Toast.makeText(this@OperatorMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showStatusSelectionDialog(block: (newStatus: OrderStatus) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Изменение статуса заказа")

        builder.setItems(OrderStatus.values().map(OrderStatus::str).toTypedArray()) { _, which ->
            block(OrderStatus.values()[which])
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
