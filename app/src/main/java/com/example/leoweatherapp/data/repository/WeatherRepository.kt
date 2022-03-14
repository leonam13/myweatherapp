package com.example.leoweatherapp.data.repository

import android.location.Location
import com.example.leoweatherapp.data.model.WeatherDetail
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(location: Location): Flow<WeatherDetail>
}