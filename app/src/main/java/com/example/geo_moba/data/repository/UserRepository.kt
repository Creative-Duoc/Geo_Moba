package com.example.geo_moba.data.repository

import com.example.geo_moba.data.local.UserDao
import com.example.geo_moba.model.UserEntity

class UserRepository(private val userDao: UserDao) {

    /**
     * Registra un nuevo usuario en la base de datos.
     * Valida que el email no exista antes de insertarlo.
     */
    suspend fun registerUser(name: String, email: String, password: String): Result<Long> {
        return try {
            if (userDao.emailExists(email)) {
                Result.failure(Exception("El correo ya está registrado"))
            } else {
                // Crear el entity y guardarlo
                val user = UserEntity(
                    name = name,
                    email = email,
                    password = password
                )
                val userId = userDao.insertUser(user)
                Result.success(userId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Intenta hacer login con las credenciales proporcionadas.
     * Consulta la base de datos y retorna el usuario si coincide.
     */
    suspend fun login(email: String, password: String): Result<UserEntity> {
        return try {
            val user = userDao.login(email, password)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene todos los usuarios (útil para debug).
     */
    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }
}

