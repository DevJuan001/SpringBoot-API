package com.example.API.controllers

import com.example.API.models.LoginRequest
import com.example.API.models.LoginResponse
import com.example.API.repositories.UsersRepository
import com.example.API.service.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val usersRepository: UsersRepository
) {
    @GetMapping("/hash")
    fun getHash(@RequestParam password: String): String {
        return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(password)
    }
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        return try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password)
            )

            // Usa el método exacto que tienes en tu repositorio
            val users = usersRepository.getUserByEmail(request.email)

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(mapOf("error" to "Usuario no encontrado"))
            }

            val user = users[0]
            val role = user.rol_name  // ← viene de tu modelo Auth

            val token = jwtService.generateToken(request.email, role)
            ResponseEntity.ok(LoginResponse(token = token, email = request.email))

        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Credenciales inválidas"))
        }
    }
}