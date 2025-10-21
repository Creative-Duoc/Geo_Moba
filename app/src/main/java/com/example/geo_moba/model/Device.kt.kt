package com.example.geo_moba.model // Paquete lógico: capa de modelos/datos.

data class Device( // data class: contenedor inmutable de datos con equals/hash/toString automáticos.
    val id: Int,       // Identificador único del dispositivo (puede mapearse a PK en Room).
    val name: String,  // Nombre visible del equipo (ej. "Tablet Israel").
    val lat: Double,   // Latitud simulada que mostraremos en UI.
    val lon: Double    // Longitud simulada que mostraremos en UI.
)
