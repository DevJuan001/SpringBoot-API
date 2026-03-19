package com.example.API.models

data class User (
    var rol_id: Int? = null,
    var rol_name: String? = null,
    var user_id: Int? = null,
    var user_name: String,
    var user_first_surname: String,
    var user_second_surname: String,
    var user_phone: String,
    var user_email: String,
    var user_password: String,
    var user_address: String,
    var user_city: String,
    var user_date: String? = null
) {
}