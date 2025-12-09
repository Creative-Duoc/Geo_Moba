package com.example.geo_moba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geo_moba.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estado de la UI de registro.
data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class RegisterViewModel(
    private val userRepository: UserRepository = UserRepository() // Inyección por constructor
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) { _uiState.value = _uiState.value.copy(name = newName, errorMessage = null) }
    fun onEmailChange(newEmail: String) { _uiState.value = _uiState.value.copy(email = newEmail, errorMessage = null) }
    fun onPasswordChange(newPassword: String) { _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null) }

    fun register() {
        val s = _uiState.value

        // Validaciones de los campos (Lógica local, fácil de testear)
        if (s.name.isBlank() || s.email.isBlank() || s.password.isBlank()) {
            _uiState.value = s.copy(errorMessage = "Complete todos los campos")
            return
        }
        
        // Esta validación depende de Android (Patterns), en test unitario puro podría fallar si no se configura Robolectric o se abstrae.
        // Para simplificar el test unitario básico, testearemos validaciones de longitud y vacíos primero.
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) {
            _uiState.value = s.copy(errorMessage = "Correo inválido")
            return
        }
        
        if (s.password.length < 6) {
            _uiState.value = s.copy(errorMessage = "Mínimo 6 caracteres")
            return
        }

        // Mostrar loading
        _uiState.value = s.copy(isLoading = true, errorMessage = null)

        // Usar coroutine para la llamada a la API
        viewModelScope.launch {
            val result = userRepository.registerUser(
                name = s.name,
                email = s.email,
                password = s.password
            )

            result.onSuccess {
                // Registro exitoso
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            }.onFailure { error ->
                // Error en el registro
                _uiState.value = s.copy(
                    isLoading = false,
                    errorMessage = error.message ?: "Error desconocido"
                )
            }
        }
    }
}
