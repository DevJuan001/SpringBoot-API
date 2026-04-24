package com.example.API.repositories

import com.example.API.models.Auth
import com.example.API.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Repository

@Repository
class UsersRepository {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun getUsers(): List<User> {
        return jdbcTemplate.query("""
            SELECT
                r.rol_id,
                r.rol_name,
                u.*
            FROM ROLES as r
            INNER JOIN USERS AS u
            ON r.rol_id = u.rol_id
            ORDER BY u.user_id DESC
            """.trimIndent(), RowMapper<User> {rs, _ ->
            User(
                rs.getInt("rol_id"),
                rs.getString("rol_name"),
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("user_first_surname"),
                rs.getString("user_second_surname"),
                rs.getString("user_phone"),
                rs.getString("user_email"),
                rs.getString("user_password"),
                rs.getString("user_address"),
                rs.getString("user_city"),
                rs.getString("user_date"),
            )
        })
    }

    fun getUserByEmail(email: String): List<Auth> {
        val sql = """
            SELECT
                r.rol_name,
                u.user_id,
                u.user_email,
                u.user_password
            FROM USERS as u
            INNER JOIN ROLES as r
            ON u.rol_id = r.rol_id
            WHERE u.user_email = ?
        """.trimIndent()

        return jdbcTemplate.query(
            sql,
            RowMapper { rs, _ ->
                Auth(
                    rs.getString("rol_name"),
                    rs.getInt("user_id"),
                    rs.getString("user_email"),
                    rs.getString("user_password")
                )
            },
            email
        )
    }

    fun createUser(user: User): Int {
        val sql = """
            INSERT INTO USERS(
            rol_id,
            user_name,
            user_first_surname,
            user_second_surname,
            user_phone,
            user_email,
            user_password,
            user_address,
            user_city)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        val password_hash = BCrypt.hashpw(user.user_password, BCrypt.gensalt(12))

        return jdbcTemplate.update(
            sql,
            user.rol_id,
            user.user_name,
            user.user_first_surname,
            user.user_second_surname,
            user.user_phone,
            user.user_email,
            password_hash,
            user.user_address,
            user.user_city
        )
    }

    fun updateUser(user: User, id: Int): Int {
        val sql = """
        UPDATE USERS SET
            user_name = ?,
            user_first_surname = ?,
            user_second_surname = ?,
            user_phone = ?,
            user_email = ?,
            user_password = ?,
            user_address = ?,
            user_city = ?
        WHERE user_id = ?
    """.trimIndent()

        val password_hash = BCrypt.hashpw(user.user_password, BCrypt.gensalt(12)) // ← agrega esto

        return jdbcTemplate.update(
            sql,
            user.user_name,
            user.user_first_surname,
            user.user_second_surname,
            user.user_phone,
            user.user_email,
            password_hash, 
            user.user_address,
            user.user_city,
            id
        )
    }
    fun deleteUser(id: Int): Int {
        val sql = """
            DELETE FROM USERS
            WHERE user_id = ?
        """.trimIndent()

        return jdbcTemplate.update(sql,
            id)
    }
}