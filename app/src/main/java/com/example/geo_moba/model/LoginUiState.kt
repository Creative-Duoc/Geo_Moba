package com.example.geo_moba.model // Capa de modelos, pero para "estado de UI".

// UiState: describe TODO lo que la pantalla necesita pintar/validar.
data class LoginUiState(
    val email: String = "",             // Valor actual del input email.
    val password: String = "",          // Valor actual del input password.
    val isLoading: Boolean = false,     // Bandera para mostrar progreso (spinner).
    val errorMessage: String? = null,   // Texto de error a mostrar (null si no hay).
    val isSuccess: Boolean = false      // Señal para navegar si el login fue exitoso.
)
