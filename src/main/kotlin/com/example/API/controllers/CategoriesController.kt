package com.example.API.controllers

import com.example.API.models.Category
import com.example.API.repositories.CategoriesRepository
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp

@RestController
@RequestMapping("/categories")
class CategoriesController(private val categoryRepository: CategoriesRepository) {

    // Obtener todas las categorías
    @GetMapping
    fun getAllCategories(): ResponseEntity<Any> {
        return ResponseEntity.ok(categoryRepository.getAll())
    }

    // Crear nueva categoría
    @PostMapping
    fun addCategory(@RequestBody category: Category): Map<String, Any> {
        val createdCategory = categoryRepository.create(category)

        return if (createdCategory > 0) {
            mapOf(
                "success" to true,
                "message" to "Category Added!"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo crear"
            )
        }
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Int, @RequestBody category: Category): Map<String, Any> {
        val updatedCategory = categoryRepository.update(category, id)

        return if (updatedCategory > 0){
            mapOf(
                "success" to true,
                "message" to "Categoria actualizada correctamente"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo actualizar la categoria"
            )
        }
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Int): Map<String, Any> {
        val deletedCategory = categoryRepository.delete(id)

        return if (deletedCategory > 0 ) {
            mapOf(
                "success" to true,
                "message" to "No se pudo eliminar la categoria"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo eliminar"
            )
        }
    }
}