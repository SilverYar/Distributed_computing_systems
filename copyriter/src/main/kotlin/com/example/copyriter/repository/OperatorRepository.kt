package com.example.copyriter.repository

import com.example.copyriter.model.storage.Operator
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OperatorRepository : CrudRepository<Operator, Long>