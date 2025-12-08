package com.example.geo_moba.data.repository

import com.example.geo_moba.data.network.ApiClient
import com.example.geo_moba.data.network.LoginRequest
import com.example.geo_moba.data.network.RegisterRequest
import com.example.geo_moba.data.network.UserResponse

class UserRepository {

    private val apiService = ApiClient.instance

    suspend fun registerUser(name: String, email: String, password: String): Result<UserResponse> {
        return try {
            val request = RegisterRequest(nombre = name, email = email, password = password)
            val response = apiService.register(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Error en el registro"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<UserResponse> {
        return try {
            val request = LoginRequest(email = email, password = password)
            val response = apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
