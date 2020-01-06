package com.example.copyriter.model.mapper

import com.example.copyriter.model.storage.Order
import com.example.copyriter.repository.ClientRepository
import com.example.copyriter.repository.OperatorRepository
import com.example.copyriter.repository.CopyriterRepository
import com.example.copyriter.model.api.Order as ApiOrder

fun Order.toApi() = ApiOrder(
    id = id,
    status = status,
    products = products,
    operatorId = operator?.id,
    clientId = client.id,
    copyriterId = copyriter?.id
)

fun ApiOrder.toDomain(copyriters: CopyriterRepository, clients: ClientRepository, operators: OperatorRepository) = Order(
    id = id,
    status = status,
    products = products,
    operator = operatorId?.let { operators.findById(it).get() },
    client = clientId.let { clients.findById(it).get() },
    copyriter = copyriterId?.let { copyriters.findById(it).get() }
)