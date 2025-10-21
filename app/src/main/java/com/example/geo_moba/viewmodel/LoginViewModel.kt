package com.example.geo_moba.viewmodel // Paquete de lógica/estado.

// ViewModel base de AndroidX.
import androidx.lifecycle.ViewModel

// StateFlow para estado observable por Compose.
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Nuestro estado de UI.
import com.example.geo_moba.model.LoginUiState

class LoginViewModel : ViewModel() { // ViewModel vive entre recomposiciones (sobrevive a rotaciones).

    // _uiState: mutable solo dentro del VM (encapsulación).
    private val _uiState = MutableStateFlow(LoginUiState())
    // uiState: exposición inmutable para la UI.
    val uiState: StateFlow<LoginUiState> = _uiState

    // Actualiza el email en el estado (inmutabilidad con copy()).
    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    // Actualiza el password en el estado.
    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    // Lógica de "iniciar sesión" simulada (sin backend).
    fun login() {
        // Limpia errores previos y activa loading.
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }

        // Regla simple de demo: credenciales fijas válidas.
        val ok = _uiState.value.email == "user@duoc.cl" && _uiState.value.password == "1234"

        // Apaga loading y setea resultado (éxito o error).
        _uiState.update {
            if (ok) it.copy(isLoading = false, isSuccess = true)
            else it.copy(isLoading = false, errorMessage = "Credenciales incorrectas")
        }
    }
}
