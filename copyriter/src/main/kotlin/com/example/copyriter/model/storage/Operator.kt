package com.example.copyriter.model.storage

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import com.example.copyriter.model.storage.Entity as DbEntity

@Entity
data class Operator(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,
    override val login: String,
    override val password: String,
    override val name: String
) : DbEntity, User