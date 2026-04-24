package com.example.API.service

import com.example.API.repositories.UsersRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val usersRepository: UsersRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val users = usersRepository.getUserByEmail(email)

        if (users.isEmpty()) {
            throw UsernameNotFoundException("Usuario no encontrado: $email")
        }

        return users[0] // Auth ya implementa UserDetails
    }
}