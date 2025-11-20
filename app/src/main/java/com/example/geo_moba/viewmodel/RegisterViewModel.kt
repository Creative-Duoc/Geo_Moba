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

// Estado de la UI de registro.
data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Obtener la instancia de la base de datos
    private val database = AppDatabase.getInstance(application)

    // 2. Obtener el UserDao de la base de datos
    private val userDao = database.userDao()

    // 3. Crear el repositorio con el UserDao
    private val userRepository = UserRepository(userDao)

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) { _uiState.value = _uiState.value.copy(name = newName, errorMessage = null) }
    fun onEmailChange(newEmail: String) { _uiState.value = _uiState.value.copy(email = newEmail, errorMessage = null) }
    fun onPasswordChange(newPassword: String) { _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null) }

    fun register() {
        val s = _uiState.value

        // Validaciones de los campos
        if (s.name.isBlank() || s.email.isBlank() || s.password.isBlank()) {
            _uiState.value = s.copy(errorMessage = "Complete todos los campos")
            return
        }
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

        // Usar coroutine para operación asíncrona
        viewModelScope.launch {
            // Llamar al repositorio para registrar el usuario en SQLite
            val result = userRepository.registerUser(
                name = s.name,
                email = s.email,
                password = s.password
            )

            // Manejar el resultado
            if (result.isSuccess) {
                // Registro exitoso
                _uiState.value = RegisterUiState(isSuccess = true)
            } else {
                // Error en el registro
                val errorMsg = result.exceptionOrNull()?.message ?: "Error al registrar"
                _uiState.value = s.copy(
                    isLoading = false,
                    errorMessage = errorMsg
                )
            }
        }
    }
}
