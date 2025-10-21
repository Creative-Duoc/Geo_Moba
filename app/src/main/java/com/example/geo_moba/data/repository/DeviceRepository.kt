package com.example.geo_moba.data.repository // Paquete de repositorios (capa Data).

import com.example.geo_moba.model.Device // Importamos el modelo de dominio.

// Repositorio que provee datos de dispositivos (por ahora simulados).
class DeviceRepository {

    // Fuente mock: simulamos que viniera de Room/API (cumple Front-End sin backend real).
    private val devices = listOf(
        Device(1, "Tablet Israel",  -33.4489, -70.6693),
        Device(2, "Smartwatch Vicente", -33.4569, -70.6483),
        Device(3, "Phone Samuel", -33.4429, -70.6503)
    )

    // Devuelve la lista de dispositivos (público para ViewModel).
    fun getDevices(): List<Device> = devices

    // Devuelve un dispositivo por id o null si no existe (para Detalle).
    fun getDeviceById(id: Int): Device? = devices.firstOrNull { it.id == id }
}
