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
import com.example.copyritersShop.model.repository.CopyritersRepository
import com.example.copyritersShop.model.repository.OrdersRepository
import com.example.copyritersShop.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.activity_copyriter_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class CopyriterMainActivity : AppCompatActivity(), OrderAdapter.OnItemClickListener, CoroutineScope {

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
                    Toast.makeText(this@CopyriterMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val adapter = OrderAdapter(this)
    private var copyriter: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copyriter_main)
        title = "Список заказов"
        val copyriterId = intent.getLongExtra("copyriter_id", -1)
        launch {
            try {
                copyriter = withContext(Dispatchers.IO) { CopyritersRepository().get(copyriterId) }
                recycler.adapter = adapter
                updateOrders()
            } catch (e: Exception) {
                Toast.makeText(this@CopyriterMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateOrders() {
        val copyriter = copyriter ?: return
        launch {
            try {
                val orders = withContext(Dispatchers.IO) {
                    OrdersRepository().getAllOrders().filter { it.status == OrderStatus.WAIT_WORKING || it.status == OrderStatus.IN_WORKING }
                }
                adapter.items.clear()
                adapter.items.addAll(orders)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this@CopyriterMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showStatusSelectionDialog(currentStatus: OrderStatus, block: (newStatus: OrderStatus) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Изменение статуса заказа")

        val statuses = when (currentStatus) {
            OrderStatus.WAIT_WORKING -> arrayOf(OrderStatus.IN_WORKING)
            OrderStatus.IN_WORKING -> arrayOf(OrderStatus.COMPLETE)
            else -> {
                Toast.makeText(this, "Смена статуса недоступна", Toast.LENGTH_SHORT).show()
                return
            }
        }
        builder.setItems(statuses.map(OrderStatus::str).toTypedArray()) { _, which ->
            block(statuses[which])
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
