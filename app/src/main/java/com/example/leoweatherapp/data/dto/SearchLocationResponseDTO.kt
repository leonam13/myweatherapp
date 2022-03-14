package com.example.leoweatherapp.data.dto

data class SearchLocationResponseDTO(
    val distance: Int,
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)