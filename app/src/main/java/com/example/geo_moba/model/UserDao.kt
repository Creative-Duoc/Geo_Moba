package com.example.geo_moba.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO (Data Access Object) para operaciones de la tabla users.
 * Define métodos para interactuar con la base de datos.
 */
@Dao
interface UserDao {

    /**
     * Inserta un nuevo usuario en la base de datos.
     * @return el ID del usuario insertado
     */
    @Insert
    suspend fun insertUser(user: User): Long

    /**
     * Busca un usuario por email y password.
     * @return el usuario si existe, null si no
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    /**
     * Verifica si un email ya está registrado.
     * @return true si el email existe, false si no
     */
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    suspend fun emailExists(email: String): Boolean

    /**
     * Obtiene todos los usuarios (útil para debug).
     */
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}

