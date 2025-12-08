package com.example.geo_moba.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geo_moba.data.repository.DeviceRepository
import com.example.geo_moba.model.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeviceViewModel : ViewModel() {
    private val repository = DeviceRepository()
    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    init {
        // Carga los dispositivos de forma síncrona al crear el ViewModel
        loadDevices()
    }

    private fun loadDevices() {
        _devices.value = repository.getDevices()
    }
}
