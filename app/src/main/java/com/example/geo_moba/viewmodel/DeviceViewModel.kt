package com.example.geo_moba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geo_moba.data.repository.DeviceRepository
import com.example.geo_moba.model.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la lógica de dispositivos.
 * Ahora maneja operaciones asíncronas con Retrofit.
 */
class DeviceViewModel : ViewModel() {

    private val repository = DeviceRepository()

    // Estado de la lista de dispositivos
    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    // Estado para manejar errores
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadDevices()
    }

    /**
     * Carga los dispositivos desde el repositorio (API).
     * Usa viewModelScope para ejecutar la corrutina en segundo plano.
     */
    fun loadDevices() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.getDevices()

            result.onSuccess { deviceList ->
                _devices.value = deviceList
            }.onFailure { exception ->
                _errorMessage.value = "Error al cargar: ${exception.message}"
                // Opcional: Podríamos dejar la lista vacía o mantener la anterior
            }

            _isLoading.value = false
        }
    }

    /**
     * Obtiene un dispositivo por ID desde la lista local cargada.
     * Si necesitas detalle fresco del servidor, podrías crear otra función suspend.
     */
    fun getDeviceById(id: Int): Device? {
        return _devices.value.find { it.id == id }
    }
}
