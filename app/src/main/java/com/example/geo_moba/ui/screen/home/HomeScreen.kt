package com.example.geo_moba.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.geo_moba.navigation.Routes

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Dispositivos Registrados", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // Simulación de lista de dispositivos
        listOf("Tablet Israel", "Smartwatch Vicente", "Phone Samuel").forEachIndexed { index, name ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { navController.navigate(Routes.Detail) }
            ) {
                Text(
                    text = "📱 $name",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = { navController.navigate(Routes.Map) }) {
            Text("Ver Mapa")
        }
    }
}
