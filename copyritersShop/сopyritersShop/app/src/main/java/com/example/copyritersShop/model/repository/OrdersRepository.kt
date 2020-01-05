package com.example.copyritersShop.model.repository

import com.example.copyritersShop.model.entity.Order
import com.example.copyritersShop.model.item.ServerErrorMessage
import com.example.copyritersShop.model.network.ApiFactory

class OrdersRepository {

    suspend fun getAllOrders(): List<Order> {
        return ApiFactory.apiService.allOrders().await()
    }

    suspend fun getOrder(requestId: Long): Order {
        val order = ApiFactory.apiService.getOrder(requestId).await()
        order.error?.let { throw ServerErrorMessage(it) }
        return order
    }

    suspend fun createOrder(order: Order): Order {
        return ApiFactory.apiService.createOrder(order).await()
    }

    suspend fun updateOrder(order: Order): Order {
        val newRequest = ApiFactory.apiService.updateOrder(order).await()
        newRequest.error?.let { throw ServerErrorMessage(it) }
        return newRequest
    }
}