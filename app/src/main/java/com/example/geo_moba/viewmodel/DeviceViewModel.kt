package com.example.geo_moba.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geo_moba.data.repository.DeviceRepository
import com.example.geo_moba.model.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel que maneja la lógica de dispositivos.
 * Obtiene los datos del DeviceRepository y los expone a la UI.
 */
class DeviceViewModel : ViewModel() {

    private val repository = DeviceRepository()

    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    init {
        loadDevices()
    }

    private fun loadDevices() {
        _devices.value = repository.getDevices()
    }

    fun getDeviceById(id: Int): Device? {
        return repository.getDeviceById(id)
    }
}

