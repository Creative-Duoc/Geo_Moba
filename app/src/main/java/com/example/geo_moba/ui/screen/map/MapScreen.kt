package com.example.geo_moba.ui.screen.map

import android.Manifest

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@Composable
fun MapScreen(
    navController: NavHostController,
    deviceViewModel: DeviceViewModel = viewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val devices by deviceViewModel.devices.collectAsState()

    // Estado para la ubicación del usuario
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Manejo de permiso de ubicación en tiempo de ejecución
    val permission = Manifest.permission.ACCESS_FINE_LOCATION

    // Verificar si ya tiene permiso guardado
    val hasPermissionGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    // Estado para saber si el usuario ya decidió (aceptó o rechazó)
    var permissionDecided by remember { mutableStateOf(hasPermissionGranted) }

    // Estado del permiso
    var hasLocationPermission by remember { mutableStateOf(hasPermissionGranted) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
        hasLocationPermission = granted
        permissionDecided = true  // Usuario tomó una decisión
    }

    // Pedir permiso solo si no lo tiene y aún no ha decidido
    LaunchedEffect(Unit) {
        if (!hasPermissionGranted) {
            launcher.launch(permission)
        }
    }

    // Cuando el permiso esté concedido, obtener la última ubicación disponible
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            try {
                val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                // Revisar proveedores disponibles y tomar la última ubicación más reciente
                val providers = lm.getProviders(true)
                var bestLocation: Location? = null
                for (provider in providers) {
                    val l = lm.getLastKnownLocation(provider)
                    if (l != null) {
                        if (bestLocation == null || l.time > bestLocation.time) {
                            bestLocation = l
                        }
                    }
                }
                bestLocation?.let { loc ->
                    userLocation = LatLng(loc.latitude, loc.longitude)
                }
            } catch (e: Exception) {
                // No bloquear la UI si falla la obtención de la ubicación
                e.printStackTrace()
            }
        }
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
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
            }
            Text("Mapa", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(Modifier.height(16.dp))

        // Contenedor del mapa que ocupa el resto del espacio
        Box(modifier = Modifier.fillMaxSize()) {
            // Mostrar el mapa solo después de que el usuario haya tomado una decisión
            if (permissionDecided) {
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

                        // Incluir ubicación del usuario solo si existe y tenemos permiso
                        if (hasLocationPermission) {
                            userLocation?.let { ul ->
                                boundsBuilder.include(ul)
                            }
                        }

                        // Centrar cámara: priorizar mostrar dispositivos siempre
                        if (devices.isNotEmpty()) {
                            try {
                                val bounds = boundsBuilder.build()
                                val padding = 150 // píxeles de margen
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
                            } catch (_: IllegalStateException) {
                                // Si hay problema construyendo bounds (un solo punto), usar zoom manual
                                val firstPosition = devices.firstOrNull()?.let { LatLng(it.latitude, it.longitude) }
                                firstPosition?.let { pos ->
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 12f))
                                }
                            }
                        } else if (hasLocationPermission && userLocation != null) {
                            // Si no hay dispositivos pero sí ubicación del usuario
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation!!, 15f))
                        } else {
                            // Posición por defecto si no hay nada (opcional)
                            val defaultPosition = LatLng(0.0, 0.0)
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 2f))
                        }

                        // Solo activar isMyLocationEnabled si tenemos permiso
                        if (hasLocationPermission) {
                            try {
                                googleMap.isMyLocationEnabled = true
                            } catch (e: SecurityException) {
                                e.printStackTrace()
                            }
                        }
                    }
                )
            } else {
                // Mostrar indicador de carga mientras el usuario decide
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Esperando respuesta de permisos...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
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
