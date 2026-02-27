package com.example.API.repositories

import com.example.API.models.Products
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository


@Repository
class ProductsRepository {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun getProducts(): List<Products> {
        return jdbcTemplate.query("SELECT * FROM get_all_products", RowMapper<Products> { rs, _ ->
            Products(
                rs.getInt("input_order_id"),
                rs.getString("input_order_bill"),
                rs.getString("category_name"),
                rs.getInt("subcategory_id"),
                rs.getString("subcategory_name"),
                rs.getInt("product_id"),
                rs.getString("product_serial"),
                rs.getString("product_detail_model"),
                rs.getInt("product_stock"),
                rs.getInt("product_details_id"),
                rs.getString("product_garanty_input")
            )
        })
    }

    fun createProducts(products: Products): Int {
        val sql = """
            INSERT INTO PRODUCTS(
             subcategory_id,
             product_details_id,
             product_stock
             )
            VALUES ( ?, ?, ?)
        """.trimIndent()



        return jdbcTemplate.update(
            sql,
            products.subcategory_id,
            products.product_stock,
            products.product_details_id,
        )
    }

    fun updateProducts(products: Products, id: Int): Int {
        val sql = """
            UPDATE PRODUCTS SET
               subcategory_id =?,
             product_id =?,
             product_stock =?,
             product_details_id =?,
            WHERE product_id = ?
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            products.subcategory_id,
            products.product_id ,
            products.product_stock,
            products.product_details_id,
            id
        )
    }

    fun deleteProducts(id: Int): Int {
        val sql = """
            DELETE FROM PRODUCTS
            WHERE product_id = ?
        """.trimIndent()

        return jdbcTemplate.update(sql, id)
    }


}