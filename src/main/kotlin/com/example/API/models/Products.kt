package com.example.API.models

data class Products (
    var input_order_id: Int,
    var input_order_bill: String? = null,
    var category_name: String? = null,
    var subcategory_id: Int,
    var subcategory_name: String? = null,
    var product_id : Int? = null,
    var product_serial: String? = null,
    var product_model: String? = null,
    var product_stock: Int,
    var product_details_id: Int,
    var product_garanty_input: String? = null
) {
}