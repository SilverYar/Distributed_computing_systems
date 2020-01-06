package com.example.copyriter.controller

import com.example.copyriter.model.api.LoginAndPassword
import com.example.copyriter.model.storage.Copyriter
import com.example.copyriter.repository.CopyriterRepository
import com.example.copyriter.util.extension.ElementAlreadyExists
import com.example.copyriter.util.extension.ElementNotFound
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/copyriters")
class CopyriterRestController(private val copyriters: CopyriterRepository) {

    @GetMapping("/all")
    fun all() = copyriters.findAll()

    @PostMapping("/create")
    fun create(@RequestBody copyriter: Copyriter): Any = when {
        copyriters.findAll().any { it.login == copyriter.login } -> ElementAlreadyExists()
        else -> copyriters.save(copyriter)
    }

    @PostMapping("/sign")
    fun signIn(@RequestBody loginAndPassword: LoginAndPassword): Any {
        val (login, password) = loginAndPassword
        return copyriters.findAll().find { it.login == login && it.password == password } ?: ElementNotFound()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: Long): Any = try {
        copyriters.findById(id).get()
    } catch (e: Exception) {
        ElementNotFound()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long) = try {
        copyriters.deleteById(id)
    } catch (e: Exception) {
        ElementNotFound()
    }
}