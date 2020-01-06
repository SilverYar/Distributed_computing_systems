package com.example.copyriter.repository

import com.example.copyriter.model.storage.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<Client, Long>