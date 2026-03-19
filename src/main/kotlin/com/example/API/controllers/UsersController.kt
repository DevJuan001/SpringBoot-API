package com.example.API.controllers

import com.example.API.middlewares.JwtFilter
import com.example.API.models.User
import com.example.API.repositories.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController() {
    @Autowired
    lateinit var userRepository: UsersRepository

    @Autowired
    lateinit var jwtFilter: JwtFilter

    @GetMapping("/users/")
    fun getUsers(@RequestHeader("Authorization", required = false) token: String): ResponseEntity<Any> {

        val error = jwtFilter.requireRole(token, "Admin")
        if (error != null) {
            return error
        }
        return ResponseEntity.ok(userRepository.getUsers())
    }

    @PostMapping("/users/create")
    fun createUser(@RequestBody user: User): Map<String, Any>{

        val createdUser = userRepository.createUser(user)

        return if (createdUser > 0 ) {
            mapOf(
                "success" to true,
                "message" to "Usuario creado correctamente"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo crear el usuario"
            )
        }
    }

    @PutMapping("/users/update/{id}")
    fun updateUser(@RequestBody user: User, @PathVariable id: Int): Map<String, Any>{
        val updatedUser = userRepository.updateUser(user, id)

        return if (updatedUser > 0){
            mapOf(
                "success" to true,
                "message" to "Usuario actualizado correctamente"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo actualizar el usuario"
            )
        }
    }

    @DeleteMapping("/users/delete/{id}")
    fun deleteUser(@PathVariable id: Int): Map<String, Any>{
        val deleteUser = userRepository.deleteUser(id)

        return if (deleteUser > 0){
            mapOf(
                "success" to true,
                "message" to "Usuario eliminado correctamente"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo eliminar el usuario"
            )
        }
    }
}