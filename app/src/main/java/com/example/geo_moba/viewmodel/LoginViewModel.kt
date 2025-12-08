package com.example.geo_moba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geo_moba.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado de la UI de login.
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

/**
 * ViewModel para la pantalla de login.
 * Maneja la autenticación de usuarios contra el backend.
 */
class LoginViewModel : ViewModel() {

    // Instancia del repositorio que se conecta a la API
    private val userRepository = UserRepository()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail, errorMessage = null)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null)
    }

    /**
     * Intenta hacer login verificando las credenciales contra el backend.
     */
    fun login() {
        val s = _uiState.value

        // Validaciones
        if (s.email.isBlank() || s.password.isBlank()) {
            _uiState.value = s.copy(errorMessage = "Complete todos los campos")
            return
        }

        _uiState.value = s.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            // Llamada al repositorio para el login remoto
            val result = userRepository.login(s.email, s.password)

            result.onSuccess {
                // Login exitoso
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            }.onFailure { error ->
                // Error en el login
                _uiState.value = s.copy(
                    isLoading = false,
                    errorMessage = error.message ?: "Credenciales incorrectas"
                )
            }
        }
    }
}
