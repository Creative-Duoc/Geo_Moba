package com.example.geo_moba.ui.screen.login // Paquete de pantallas.

import androidx.compose.foundation.layout.* // Layouts (Column, Spacer, etc).
import androidx.compose.material3.*        // Componentes Material 3.
import androidx.compose.runtime.*          // Estados Compose (@Composable).
import androidx.compose.ui.Modifier        // Modificador para tamaños/estilos.
import androidx.compose.ui.text.input.PasswordVisualTransformation // Ocultar password.
import androidx.compose.ui.unit.dp         // Dimensiones.
import androidx.lifecycle.viewmodel.compose.viewModel // Obtener ViewModel en Compose.
import androidx.navigation.NavHostController // Navegación en Compose.
import com.example.geo_moba.navigation.Routes // Rutas centralizadas.
import com.example.geo_moba.viewmodel.LoginViewModel // Nuestro VM de login.

@Composable
fun LoginScreen(navController: NavHostController, vm: LoginViewModel = viewModel()) {
    val state by vm.uiState.collectAsState() // Observa StateFlow<LoginUiState> (recompone al cambiar).

    Column(Modifier.padding(24.dp)) { // Contenedor vertical con padding.
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall) // Título.

        Spacer(Modifier.height(16.dp)) // Separación visual.

        TextField( // Campo de email.
            value = state.email, // Valor actual (binding a UiState).
            onValueChange = vm::onEmailChange, // Actualiza en el ViewModel.
            label = { Text("Correo") }, // Etiqueta del campo.
            singleLine = true, // Una sola línea.
            modifier = Modifier.fillMaxWidth() // Ocupar ancho completo.
        )

        Spacer(Modifier.height(8.dp)) // Separación.

        TextField( // Campo de password.
            value = state.password,
            onValueChange = vm::onPasswordChange,
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(), // Oculta caracteres.
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button( // Botón principal.
            onClick = { vm.login() }, // Ejecuta lógica del VM.
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        // Indicador de carga si isLoading = true.
        if (state.isLoading) {
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        // Muestra error si lo hay.
        state.errorMessage?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        // Si login fue exitoso, navegamos a Home.
        if (state.isSuccess) {
            // OJO: navegar en un effect para evitar múltiples navegaciones en recomposición
            LaunchedEffect(Unit) {
                navController.navigate(Routes.Home) {
                    popUpTo(Routes.Login) { inclusive = true } // Evita volver al Login con back.
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        TextButton(onClick = { navController.navigate(Routes.Register) }) { // Link a Register.
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}
