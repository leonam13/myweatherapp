package com.example.leoweatherapp.data.source

import com.example.leoweatherapp.data.model.WeatherDetail

interface WeatherDataSource {

    suspend fun getLocationId(lattlong: String): Int

    suspend fun getWeather(locationId: Int): WeatherDetail

    fun saveLastLocationId(locationId: Int)
}