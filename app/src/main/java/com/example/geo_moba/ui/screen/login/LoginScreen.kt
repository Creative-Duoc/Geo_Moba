package com.example.geo_moba.ui.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment // Importación necesaria para centrar
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight // Importación para negrita
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Importación para tamaño de fuente
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.geo_moba.navigation.Routes
import com.example.geo_moba.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController, vm: LoginViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()

    // 1. Contenedor principal para centrar todo el contenido
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .padding(24.dp),
        // 2. Centrado vertical (para centrar el formulario en la pantalla)
        verticalArrangement = Arrangement.Center,
        // 3. Centrado horizontal (para centrar los elementos dentro de la columna)
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- TÍTULO GEO-MOBA ---
        Text(
            text = "GEO-MOBA",
            fontSize = 48.sp, // Tamaño grande
            fontWeight = FontWeight.Bold, // Estilo negrita
            color = MaterialTheme.colorScheme.primary, // Usamos el color principal del tema
            modifier = Modifier.padding(bottom = 64.dp) // Espacio generoso debajo del título
        )

        // 4. Se eliminó el "Iniciar Sesión" pequeño, ya que el GEO-MOBA es el título principal.
        // Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall)

        // --- CAMPOS DE TEXTO ---

        TextField( // Campo de email.
            value = state.email,
            onValueChange = vm::onEmailChange,
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth() // Mantiene el ancho completo
        )

        Spacer(Modifier.height(8.dp)) // Separación.

        TextField( // Campo de password.
            value = state.password,
            onValueChange = vm::onPasswordChange,
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth() // Mantiene el ancho completo
        )

        Spacer(Modifier.height(16.dp))

        // --- BOTÓN DE INGRESO ---

        Button(
            onClick = { vm.login() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        // --- FEEDBACK Y NAVEGACIÓN ---

        if (state.isLoading) {
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        state.errorMessage?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        if (state.isSuccess) {
            LaunchedEffect(Unit) {
                navController.navigate(Routes.Home) {
                    popUpTo(Routes.Login) { inclusive = true }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        TextButton(onClick = { navController.navigate(Routes.Register) }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}