package com.example.leoweatherapp.data.source.remote

import com.example.leoweatherapp.data.dto.GetWeatherResponseDTO
import com.example.leoweatherapp.data.dto.SearchLocationResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/api/location/search/?lattlong=")
    suspend fun getLocationId(@Query("lattlong") lattlong: String): List<SearchLocationResponseDTO>

    @GET("/api/location/{locationId}")
    suspend fun getWeather(@Path("locationId") locationId: Int): GetWeatherResponseDTO
}