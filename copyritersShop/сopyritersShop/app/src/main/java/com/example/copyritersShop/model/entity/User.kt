package com.example.copyritersShop.model.entity

import com.google.gson.annotations.Expose

data class User(
        @Expose val id: Long? = null,
        @Expose val login: String? = null,
        @Expose val password: String? = null,
        @Expose val name: String? = null,
        @Expose val error: String? = null
)