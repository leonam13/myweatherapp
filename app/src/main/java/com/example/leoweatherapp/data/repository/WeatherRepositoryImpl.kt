package com.example.leoweatherapp.data.repository

import android.content.Context
import android.location.Location
import com.example.leoweatherapp.data.model.WeatherDetail
import com.example.leoweatherapp.data.source.WeatherDataSource
import com.example.leoweatherapp.exceptions.NotNetworkException
import com.example.leoweatherapp.utils.hasNetwork
import com.example.leoweatherapp.utils.toQueryParam
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @ApplicationContext val applicationContext: Context,
    private val remoteDataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getWeather(location: Location): Flow<WeatherDetail> = flow {
        if (applicationContext.hasNetwork())
            remoteDataSource.getLocationId(location.toQueryParam()).let { id ->
                emit(remoteDataSource.getWeather(id))
            }
        else throw NotNetworkException()
    }
}