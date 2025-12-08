package com.example.geo_moba.data.repository

import com.example.geo_moba.model.Device

class DeviceRepository {
    // Lista de dispositivos simulados directamente en el repositorio
    private val devices = listOf(
        Device(1, "Tablet Israel", -33.5505, -70.5831),      // La Florida
        Device(2, "Smartwatch Vicente", -33.4316, -70.6091), // Providencia
        Device(3, "Phone Samuel", -33.4475, -70.6729)        // Santiago Centro
    )

    // Función simple que devuelve la lista
    fun getDevices(): List<Device> {
        return devices
    }
}
