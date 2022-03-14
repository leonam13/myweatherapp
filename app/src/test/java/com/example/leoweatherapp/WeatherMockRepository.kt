package com.example.leoweatherapp

import android.location.Location
import com.example.leoweatherapp.data.model.WeatherDetail
import com.example.leoweatherapp.data.model.WeatherDetailTemperature
import com.example.leoweatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherMockRepository : WeatherRepository {

    override suspend fun getWeather(location: Location): Flow<WeatherDetail> {
        return flow {
            emit(
                WeatherDetail(
                    city = "Sao Paulo",
                    temperature = listOf(
                        WeatherDetailTemperature(
                            32,
                            32,
                            18,
                            "Clear",
                            "https://www.metaweather.com/static/img/weather/png/c.png"
                        )
                    )
                )
            )
        }
    }
}