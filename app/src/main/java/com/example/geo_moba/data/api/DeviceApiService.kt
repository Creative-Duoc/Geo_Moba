package com.example.geo_moba.data.api

import com.example.geo_moba.model.Device
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz que define los endpoints para conectarse con el microservicio de Spring Boot.
 * Retrofit generará la implementación automáticamente.
 */
interface DeviceApiService {

    // Obtener todos los dispositivos
    @GET("api/devices")
    suspend fun getAllDevices(): Response<List<Device>>

    // Obtener un dispositivo por ID
    @GET("api/devices/{id}")
    suspend fun getDeviceById(@Path("id") id: Int): Response<Device>

    // Crear un nuevo dispositivo
    @POST("api/devices")
    suspend fun createDevice(@Body device: Device): Response<Device>

    // Actualizar un dispositivo
    @PUT("api/devices/{id}")
    suspend fun updateDevice(@Path("id") id: Int, @Body device: Device): Response<Device>

    // Eliminar un dispositivo
    @DELETE("api/devices/{id}")
    suspend fun deleteDevice(@Path("id") id: Int): Response<Void>
}
