package com.example.geo_moba.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto Singleton que configura y provee la instancia de Retrofit.
 */
object RetrofitClient {

    // URL base para el emulador Android (conecta al localhost de tu PC)
    // Si usas un dispositivo físico, cambia esto por la IP de tu PC (ej: "http://192.168.1.15:8080/")
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val deviceApiService: DeviceApiService by lazy {
        retrofit.create(DeviceApiService::class.java)
    }
}
