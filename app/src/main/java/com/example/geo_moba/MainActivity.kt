package com.example.geo_moba // Paquete raíz de Android.

import android.os.Bundle // Ciclo de vida de Activity.
import androidx.activity.ComponentActivity // Activity base para Compose.
import androidx.activity.compose.setContent // Permite setear contenido Compose.
import com.example.geo_moba.GeoMobaApp // Import del composable raíz.

class MainActivity : ComponentActivity() { // Punto de entrada de Android (manifest).
    override fun onCreate(savedInstanceState: Bundle?) { // Se llama al crear la Activity.
        super.onCreate(savedInstanceState) // Llama a la implementación base.

        setContent { // Reemplaza setContentView con Compose.
            GeoMobaApp() // Monta el árbol de Composables comenzando por nuestra App.
        }
    }
}
