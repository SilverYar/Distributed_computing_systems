package com.example.copyritersShop.model.repository

import com.example.copyritersShop.model.entity.User
import com.example.copyritersShop.model.network.ApiFactory

class CopyritersRepository {

    suspend fun getAll(): List<User> =
            ApiFactory.apiService.allCopyriters().await()

    suspend fun get(id: Long) =
            ApiFactory.apiService.allCopyriters().await().find { it.id == id }!!
}