package com.example.leoweatherapp.data.source.remote

import com.example.leoweatherapp.data.model.WeatherDetail
import com.example.leoweatherapp.data.source.WeatherDataSource
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val service: WeatherAPI) :
    WeatherDataSource {

    override suspend fun getLocationId(lattlong: String): Int {
        return service.getLocationId(lattlong).first().woeid
    }

    override suspend fun getWeather(locationId: Int): WeatherDetail {
        return service.getWeather(locationId).toWeatherDetail()
    }

    override fun saveLastLocationId(locationId: Int) {
        //NO-OP
    }
}