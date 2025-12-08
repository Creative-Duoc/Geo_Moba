package com.example.geo_moba.data.repository

import com.example.geo_moba.data.api.RetrofitClient
import com.example.geo_moba.model.Device

/**
 * Repositorio encargado de gestionar los datos de los dispositivos.
 * Se comunica con la API a través de RetrofitClient.
 */
class DeviceRepository {

    private val api = RetrofitClient.deviceApiService

    // Obtener todos los dispositivos
    suspend fun getDevices(): Result<List<Device>> {
        return try {
            val response = api.getAllDevices()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error al obtener dispositivos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener un solo dispositivo
    suspend fun getDeviceById(id: Int): Result<Device> {
        return try {
            val response = api.getDeviceById(id)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Dispositivo no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Crear dispositivo (CRUD)
    suspend fun createDevice(device: Device): Result<Device> {
        return try {
            val response = api.createDevice(device)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Error al crear dispositivo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Actualizar dispositivo (CRUD)
    suspend fun updateDevice(id: Int, device: Device): Result<Device> {
        return try {
            val response = api.updateDevice(id, device)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Error al actualizar dispositivo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Eliminar dispositivo (CRUD)
    suspend fun deleteDevice(id: Int): Result<Boolean> {
        return try {
            val response = api.deleteDevice(id)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Error al eliminar dispositivo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
