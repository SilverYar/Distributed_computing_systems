package com.example.copyriter.repository

import com.example.copyriter.model.storage.Copyriter
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CopyriterRepository : CrudRepository<Copyriter, Long>