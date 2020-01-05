package com.example.copyritersShop.model.repository

import com.example.copyritersShop.model.entity.LoginData
import com.example.copyritersShop.model.entity.User
import com.example.copyritersShop.model.item.AuthorizationFailException
import com.example.copyritersShop.model.item.ServerErrorMessage
import com.example.copyritersShop.model.item.UserType
import com.example.copyritersShop.model.network.ApiFactory

class AuthRepository {
    suspend fun loginUser(type: UserType, login: String, password: String): User {
        val responseBody = LoginData(login, password)
        val user = when (type) {
            UserType.CLIENT -> ApiFactory.apiService.loginClient(responseBody).await()
            UserType.COPYRITER -> ApiFactory.apiService.loginCopyriter(responseBody).await()
            UserType.OPERATOR -> ApiFactory.apiService.loginOperator(responseBody).await()
        }
        user.error?.let { throw AuthorizationFailException() }
        return user
    }

    suspend fun registerUser(type: UserType, user: User): User {
        val user = when (type) {
            UserType.CLIENT -> ApiFactory.apiService.registerClient(user).await()
            UserType.COPYRITER -> ApiFactory.apiService.registerCopyriter(user).await()
            UserType.OPERATOR -> ApiFactory.apiService.registerOperator(user).await()
        }
        user.error?.let { throw ServerErrorMessage("Пользователь уже существует") }
        return user
    }
}