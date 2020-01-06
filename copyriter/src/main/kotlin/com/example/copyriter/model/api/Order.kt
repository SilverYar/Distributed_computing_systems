package com.example.copyriter.model.api

import com.example.copyriter.model.storage.OrderStatus

data class Order(
    val id: Long,
    val products: ArrayList<String> = arrayListOf(),
    var status: OrderStatus,
    val operatorId: Long? = null,
    val clientId: Long,
    var copyriterId: Long? = null
)