package com.example.copyritersShop.model.repository

import com.example.copyritersShop.model.entity.User
import com.example.copyritersShop.model.network.ApiFactory

class ClientsRepository {

    suspend fun getAll(): List<User> =
            ApiFactory.apiService.allClients().await()

    suspend fun get(id: Long) =
            ApiFactory.apiService.allClients().await().find { it.id == id }!!
}