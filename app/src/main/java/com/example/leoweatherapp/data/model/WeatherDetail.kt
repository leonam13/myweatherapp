package com.example.leoweatherapp.data.model

data class WeatherDetail(
    val city: String,
    val temperature: List<WeatherDetailTemperature>
)

data class WeatherDetailTemperature(
    val current: Int,
    val max: Int,
    val min: Int,
    val state: String,
    val stateIcon: String
)