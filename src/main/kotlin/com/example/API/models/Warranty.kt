package com.example.API.models

data class Warranty(
    var warranty_incidents_id: Int,
    var product_serial: String,
    var warranty_customer:String,
    var warranty_phone: String,
    var warranty_address: String,
    var warranty_description: String,
    var warranty_link_attachments: String,
    var warranty_city: String,
    var warranty_date: String?=null,
    var warranty_status: Int? = null

) {

}