package com.example.geo_moba.model

/**
 * Modelo de datos para un dispositivo.
 * @param id Identificador único del dispositivo
 * @param name Nombre del dispositivo
 * @param latitude Latitud de la ubicación del dispositivo
 * @param longitude Longitud de la ubicación del dispositivo
 */
data class Device(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)

