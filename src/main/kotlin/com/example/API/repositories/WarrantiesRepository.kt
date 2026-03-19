package com.example.API.repositories

import com.example.API.models.Warranty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class WarrantiesRepository {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun getWarranties(): List <Warranty>{
        return jdbcTemplate.query("SELECT * FROM WARRANTY_INCIDENTS", RowMapper<Warranty> {rs,_->
            Warranty(
                rs.getInt("warranty_incidents_id"),
                rs.getString("product_serial"),
                rs.getString("warranty_customer"),
                rs.getString("warranty_phone"),
                rs.getString("warranty_address"),
                rs.getString("warranty_description"),
                rs.getString("warranty_link_attachments"),
                rs.getString("warranty_city"),
                rs.getString("warranty_date"),
                rs.getInt("warranty_status")

            )
        }
      )
    }

    fun createWarranty(warranty: Warranty): Int{
        val sql = """
            INSERT INTO WARRANTY_INCIDENTS(
             warranty_incidents_id ,
              product_serial,
              warranty_customer ,
              warranty_phone ,
              warranty_address ,
              warranty_description ,
              warranty_link_attachments,
              warranty_city,
              warranty_date,
              warranty_status)
              VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            warranty.warranty_incidents_id,
            warranty.product_serial,
            warranty.warranty_customer,
            warranty.warranty_phone,
            warranty.warranty_address,
            warranty.warranty_description,
            warranty.warranty_link_attachments,
            warranty.warranty_city,
            warranty.warranty_date,
            warranty.warranty_status
          )
    }
    fun updateWarranty(warranty: Warranty, id: Int): Int{
        val sql="""
            UPDATE WARRANTY_INCIDENTS SET
             product_serial = ?,
              warranty_customer = ? ,
              warranty_phone = ?,
              warranty_address = ?,
              warranty_description = ?,
              warranty_link_attachments = ?,
              warranty_city = ?,
              warranty_date = ?,
              warranty_status = ?
              WHERE  warranty_incidents_id = ?
              
        """.trimIndent()
        return jdbcTemplate.update(
            sql,
            warranty.product_serial,
            warranty.warranty_customer,
            warranty.warranty_phone,
            warranty.warranty_address,
            warranty.warranty_description,
            warranty.warranty_link_attachments,
            warranty.warranty_city,
            warranty.warranty_date,
            warranty.warranty_status,
            id
        )

    }
    fun deleteWarranty(id: Int): Int{
        val sql = """
            DELETE FROM WARRANTY_INCIDENTS WHERE warranty_incidents_id=?
        """.trimIndent()
        return jdbcTemplate.update(sql, id)
    }
}
