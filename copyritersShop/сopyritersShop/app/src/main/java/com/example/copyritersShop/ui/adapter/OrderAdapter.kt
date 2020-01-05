package com.example.copyritersShop.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.copyritersShop.R
import com.example.copyritersShop.model.entity.Order
import com.example.copyritersShop.ui.view.OrderViewHolder

class OrderAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<OrderViewHolder>() {
    val items = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { listener.onItemClick(items[position]) }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Order)
    }
}