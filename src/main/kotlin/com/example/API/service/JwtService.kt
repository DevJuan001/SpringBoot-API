package com.example.API.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.Date

@Service
class JwtService {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    private val expirationMs = 86_400_000L // 24 horas

    private fun getKey() = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun requireRole(token: String?, requiredRole: String): ResponseEntity<Any>? {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Token requerido"))
        }

        val jwt = token.substring(7)

        if (!validateToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Token inválido o expirado"))
        }

        val role = extractRole(jwt)
        if (role != requiredRole) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(mapOf("error" to "No tienes permisos para esta acción"))
        }

        return null // todo OK
    }

    fun extractRole(token: String): String {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload["role"] as? String ?: "User"
    }
    fun generateToken(email: String, role: String): String {
        return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(getKey())
            .compact()
    }

    fun extractEmail(token: String): String {
        return Jwts.parser()                         // ← antes era .parserBuilder()
            .verifyWith(getKey())                    // ← antes era .setSigningKey()
            .build()
            .parseSignedClaims(token)                // ← antes era .parseClaimsJws()
            .payload
            .subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}