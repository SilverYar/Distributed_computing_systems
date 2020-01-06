package com.example.copyriter.repository

import com.example.copyriter.model.storage.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<Order, Long>