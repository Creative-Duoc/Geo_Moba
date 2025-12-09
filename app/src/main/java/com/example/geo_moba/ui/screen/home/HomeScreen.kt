package com.example.geo_moba.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.geo_moba.navigation.Routes
import com.example.geo_moba.viewmodel.DeviceViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    deviceViewModel: DeviceViewModel = viewModel()
) {
    val devices by deviceViewModel.devices.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Dispositivos Registrados", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            // Lista de dispositivos desde el repositorio
            if (devices.isEmpty()) {
                Text("No hay dispositivos o cargando...", style = MaterialTheme.typography.bodyMedium)
            } else {
                devices.forEach { device ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)

                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "📱 ${device.name}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Lat: ${"%.4f".format(device.latitude)}, Lng: ${"%.4f".format(device.longitude)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate(Routes.Map) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Mapa")
            }
        }

        // Botón de cerrar sesión en la esquina superior derecha
        IconButton(
            onClick = {
                // Navega al Login y limpia el back stack completo
                navController.navigate(Routes.Login) {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Cerrar Sesión"
            )
        }
    }
}
