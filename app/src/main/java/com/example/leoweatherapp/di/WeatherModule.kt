package com.example.leoweatherapp.di

import android.content.Context
import com.example.leoweatherapp.data.repository.WeatherRepository
import com.example.leoweatherapp.data.repository.WeatherRepositoryImpl
import com.example.leoweatherapp.data.source.remote.WeatherAPI
import com.example.leoweatherapp.data.source.remote.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherAPI(retrofit: Retrofit): WeatherAPI = retrofit.create(WeatherAPI::class.java)

    @Provides
    @Singleton
    fun provideWeatherRemoteDataSource(IdeaService: WeatherAPI): WeatherRemoteDataSource =
        WeatherRemoteDataSource(IdeaService)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        @ApplicationContext applicationContext: Context,
        remote: WeatherRemoteDataSource,
    ): WeatherRepository = WeatherRepositoryImpl(applicationContext, remote)
}