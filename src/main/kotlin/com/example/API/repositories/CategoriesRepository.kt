package com.example.API.repositories

import com.example.API.models.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CategoriesRepository {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun getAll(): List<Category> {
        return jdbcTemplate.query(
            "SELECT * FROM categories",
            RowMapper<Category> { rs, _ ->
                Category(
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("category_date")
                )
            })
    }

    fun create(category: Category) : Int {
        return jdbcTemplate.update(
            "INSERT INTO categories (category_name) VALUES (?)",
            category.category_name
        )
    }

    fun update(category: Category, id: Int) : Int {
        return jdbcTemplate.update(
            "UPDATE categories SET category_name = ? WHERE category_id = ?",
            category.category_name,
            id
        )
    }

    fun delete(id: Int) : Int {
        return jdbcTemplate.update(
            "DELETE FROM categories WHERE category_id = ?",
            id
        )
    }
}