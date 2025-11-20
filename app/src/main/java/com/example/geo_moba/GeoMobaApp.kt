package com.example.geo_moba // Nivel raíz de la app.

import androidx.compose.material3.MaterialTheme // Tema Material.
import androidx.compose.material3.Surface       // Contenedor base de fondo/tonos.
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController // Crea NavController en Compose.
import com.example.geo_moba.navigation.GeoMobaNavGraph  // Nuestro grafo de navegación.

@Composable
fun GeoMobaApp() { // Función raíz que compone toda la UI.
    val navController = rememberNavController() // Crea/recuerda el controlador (sobrevive recomposición).

    MaterialTheme { // Aplica el tema global (colores/tipografías).
        Surface {   // Superficie base (fondo, eleva contenido).
            GeoMobaNavGraph(navController) // Monta el grafo de navegación.
        }
    }
}
