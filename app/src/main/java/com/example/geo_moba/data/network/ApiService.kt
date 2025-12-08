package com.example.geo_moba.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("clients")
    suspend fun register(@Body request: RegisterRequest): Response<UserResponse>

    @POST("clients/login")
    suspend fun login(@Body request: LoginRequest): Response<UserResponse>
}

