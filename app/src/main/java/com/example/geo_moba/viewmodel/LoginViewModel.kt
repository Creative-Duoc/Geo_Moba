package com.example.geo_moba.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.geo_moba.data.local.AppDatabase
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
 * Maneja la autenticación de usuarios contra la base de datos local.
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Instancia de la base de datos y repositorio (Room)
    private val database = AppDatabase.getInstance(application)
    private val userDao = database.userDao()
    private val userRepository = UserRepository(userDao)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail, errorMessage = null)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null)
    }

    /**
     * Intenta hacer login verificando las credenciales en la base de datos.
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
            try {
                // Consulta real a través del repositorio
                val result = userRepository.login(s.email, s.password)

                if (result.isSuccess) {
                    // Login exitoso
                    _uiState.value = LoginUiState(isSuccess = true)
                } else {
                    val err = result.exceptionOrNull()?.message ?: "Usuario o contraseña incorrectos"
                    _uiState.value = s.copy(isLoading = false, errorMessage = err)
                }
            } catch (e: Exception) {
                _uiState.value = s.copy(isLoading = false, errorMessage = "Error al acceder a la base de datos")
            }
        }
    }
}
