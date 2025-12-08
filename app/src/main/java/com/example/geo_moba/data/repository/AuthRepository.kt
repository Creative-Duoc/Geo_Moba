package com.example.geo_moba.data.repository

import com.example.geo_moba.data.network.ApiService
import com.example.geo_moba.data.network.RegisterRequest
import com.example.geo_moba.data.network.UserResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    suspend fun register(name: String, email: String, password: String): Response<UserResponse> {
        val request = RegisterRequest(nombre = name, email = email, password = password)
        return apiService.register(request)
    }
}

