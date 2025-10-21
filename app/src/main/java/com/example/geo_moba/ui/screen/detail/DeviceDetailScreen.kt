package com.example.geo_moba.ui.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.geo_moba.navigation.Routes

@Composable
fun DeviceDetailScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Detalle del Dispositivo", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        Text("Latitud: -33.4489")
        Text("Longitud: -70.6693")
        Spacer(Modifier.height(24.dp))
        Button(onClick = { navController.navigate(Routes.History) }) {
            Text("Ver historial")
        }
    }
}
