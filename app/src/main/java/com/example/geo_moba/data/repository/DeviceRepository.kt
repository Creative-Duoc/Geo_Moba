package com.example.geo_moba.data.repository // Paquete de repositorios (capa Data).

import com.example.geo_moba.model.Device

class DeviceRepository {


    private val devices = listOf(
        Device(1, "Tablet Israel",  -33.4489, -70.6693),
        Device(2, "Smartwatch Vicente", -33.4569, -70.6483),
        Device(3, "Phone Samuel", -33.4429, -70.6503)
    )


    fun getDevices(): List<Device> = devices


    fun getDeviceById(id: Int): Device? = devices.firstOrNull { it.id == id }
}
