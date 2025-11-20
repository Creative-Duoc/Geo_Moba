package com.example.geo_moba.ui.screen.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.core.content.ContextCompat
import com.example.geo_moba.viewmodel.DeviceViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    navController: NavHostController,
    deviceViewModel: DeviceViewModel = viewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val devices by deviceViewModel.devices.collectAsState()

    // Manejo de permiso de ubicación en tiempo de ejecución
    var hasLocationPermission by remember { mutableStateOf(false) }
    val permission = Manifest.permission.ACCESS_FINE_LOCATION

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
        hasLocationPermission = granted
    }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        if (granted) hasLocationPermission = true else launcher.launch(permission)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Barra superior con botón atrás que usa navController
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
            Text("Mapa", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(Modifier.height(16.dp))

        // Contenedor del mapa que ocupa el resto del espacio
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapView(
                lifecycleOwner = lifecycleOwner,
                onMapReady = { googleMap ->
                    // Configuración inicial del mapa
                    googleMap.uiSettings.isZoomControlsEnabled = true

                    // Añadir marcadores desde los dispositivos del repositorio
                    val boundsBuilder = LatLngBounds.Builder()

                    devices.forEach { device ->
                        val position = LatLng(device.latitude, device.longitude)

                        googleMap.addMarker(
                            MarkerOptions()
                                .position(position)
                                .title(device.name)
                                .snippet("ID: ${device.id}")
                        )

                        boundsBuilder.include(position)
                    }

                    // Centrar cámara para mostrar todos los dispositivos
                    if (devices.isNotEmpty()) {
                        try {
                            val bounds = boundsBuilder.build()
                            val padding = 150 // píxeles de margen
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
                        } catch (e: IllegalStateException) {
                            // Si solo hay un dispositivo, usar zoom manual
                            val firstDevice = devices.first()
                            val position = LatLng(firstDevice.latitude, firstDevice.longitude)
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12f))
                        }
                    }

                    // Habilitar my-location si el permiso está concedido
                    if (hasLocationPermission) {
                        try {
                            googleMap.isMyLocationEnabled = true
                        } catch (e: SecurityException) {
                            e.printStackTrace()
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun GoogleMapView(
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    onMapReady: (GoogleMap) -> Unit
) {
    val context = LocalContext.current
    // Crear MapView una sola vez
    val mapView = remember { MapView(context) }

    // Vincular eventos del ciclo de vida al MapView para evitar leaks
    DisposableEffect(lifecycleOwner, mapView) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize()) { mv ->
        mv.getMapAsync { gm ->
            onMapReady(gm)
        }
    }
}
