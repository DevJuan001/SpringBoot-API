package com.example.API.controllers

import com.example.API.models.Products
import com.example.API.repositories.ProductsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsController {
    @Autowired
    lateinit var productsRepository: ProductsRepository

    @GetMapping("/products")
    fun getProducts(): List<Products> {
        return productsRepository.getProducts()
    }

    @PostMapping("/products")
    fun createProducts(@RequestBody products: Products): Map<String, Any>{

        val createdProducts = productsRepository.createProducts(products)

        return if (createdProducts > 0 ) {
            mapOf(
                "success" to true,
                "message" to "Producto creado correctamente"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo crear el producto"
            )
        }
    }

    @PutMapping("/products/{id}")
    fun updateProducts(@RequestBody products: Products, @PathVariable id: Int): Map<String, Any>{
        val updatedProducts = productsRepository.updateProducts(products, id)

        return if (updatedProducts > 0){
            mapOf(
                "success" to true,
                "message" to "Producto actualizado correctamente"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo actualizar el usuario"
            )
        }
    }

    @DeleteMapping("/products/{id}")
    fun deleteProducts(@PathVariable id: Int): Map<String, Any>{
        val deleteProducts = productsRepository.deleteProducts(id)

        return if (deleteProducts > 0){
            mapOf(
                "success" to true,
                "message" to "Producto eliminado correctamente"
            )
        } else {
            mapOf(
                "success" to false,
                "message" to "No se pudo eliminar el producto"
            )
        }
    }


}