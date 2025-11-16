package com.example.geo_moba.ui.screen.history // cambia por profile, settings, etc.

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import android.content.res.Configuration

@Composable
fun HistoryScreen() { // cambia por ProfileScreen, etc.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Historial de ubicaciones", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("Aquí se mostrarán las ubicaciones guardadas.")
    }
}

