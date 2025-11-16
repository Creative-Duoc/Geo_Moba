package com.example.geo_moba.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.geo_moba.model.AppDatabase
import com.example.geo_moba.model.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado de la UI de registro.
 */
data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

/**
 * ViewModel para la pantalla de registro.
 * Maneja la lógica de registro de usuarios.
 */
class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName, errorMessage = null)
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail, errorMessage = null)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null)
    }

    /**
     * Intenta registrar al usuario.
     * Valida los campos y llama al repository.
     */
    fun register() {
        val currentState = _uiState.value

        // Validaciones
        if (currentState.name.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "El nombre es obligatorio")
            return
        }

        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "El correo es obligatorio")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            _uiState.value = currentState.copy(errorMessage = "Correo electrónico inválido")
            return
        }

        if (currentState.password.length < 6) {
            _uiState.value = currentState.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
            return
        }

        // Intentar registro
        _uiState.value = currentState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = repository.registerUser(
                name = currentState.name,
                email = currentState.email,
                password = currentState.password
            )

            result.fold(
                onSuccess = {
                    _uiState.value = RegisterUiState(isSuccess = true)
                },
                onFailure = { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error al registrar"
                    )
                }
            )
        }
    }
}

