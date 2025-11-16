package com.example.geo_moba.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa un usuario en la base de datos local.
 * Room usará esta clase para crear la tabla 'users'.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
)

