package com.example.geo_moba.ui.screen.splash

import androidx.compose.foundation.layout.*            // Para Column, Spacer, etc.
import androidx.compose.material3.*                   // Para Material Design 3
import androidx.compose.runtime.*                     // Para estados Compose
import androidx.compose.ui.Alignment                 // Alinear contenido
import androidx.compose.ui.Modifier                  // Modificadores de estilo
import androidx.compose.ui.text.font.FontWeight      // Peso de texto
import androidx.compose.ui.unit.dp                   // Unidades de padding
import androidx.compose.ui.unit.sp                   // Tamaño de texto
import androidx.navigation.NavHostController         // Navegación
import kotlinx.coroutines.delay                      // Corrutina para pausa
import com.example.geo_moba.navigation.Routes        // Rutas de navegación

@Composable
fun SplashScreen(navController: NavHostController) {
    // Efecto que se ejecuta una vez (cuando se muestra el composable)
    LaunchedEffect(Unit) {
        delay(2500) // Espera 2.5 segundos para simular carga inicial
        navController.navigate(Routes.Login) { // Navega al Login
            popUpTo(Routes.Splash) { inclusive = true } // Quita Splash del stack
        }
    }

    // UI de la pantalla Splash
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "🌍 Geo-Moba",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Iniciando aplicación...",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator() // Indicador de carga
        }
    }
}
