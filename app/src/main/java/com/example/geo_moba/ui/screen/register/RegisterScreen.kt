package com.example.geo_moba.ui.screen.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment // Importación necesaria para centrar
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight // Importación para negrita
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Importación para tamaño de fuente
import androidx.navigation.NavHostController
import com.example.geo_moba.navigation.Routes

@Composable
fun RegisterScreen(navController: NavHostController) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .padding(24.dp),
        // Centrado vertical (para centrar el formulario en la pantalla)
        verticalArrangement = Arrangement.Center,
        // Centrado horizontal (para centrar los elementos dentro de la columna)
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- TÍTULO GEO-MOBA ---
        Text(
            text = "GEO-MOBA",
            fontSize = 48.sp, // Tamaño grande
            fontWeight = FontWeight.Bold, // Estilo negrita
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 48.dp) // Menos espacio que en login porque hay más campos
        )

        // --- CAMPOS DE TEXTO ---

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        // --- BOTONES ---

        Button(
            onClick = { navController.navigate(Routes.Home) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver al inicio de sesión")
        }

        // --- MENSAJE DE AGRADECIMIENTO ---
        Spacer(Modifier.height(48.dp)) // Espacio para separar el formulario
        Text(
            text = "Gracias por usar nuestra aplicación.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}