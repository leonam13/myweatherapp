package com.example.leoweatherapp

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leoweatherapp.data.Result
import com.example.leoweatherapp.data.model.WeatherDetail
import com.example.leoweatherapp.data.repository.WeatherRepository
import com.example.leoweatherapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    val weatherResult = SingleLiveEvent<Result<WeatherDetail>>()

    fun getWeather(location: Location?) {
        viewModelScope.launch {
            if (location != null) {
                weatherResult.value = Result.Loading
                repository.getWeather(location)
                    .catch { error ->
                        error.printStackTrace()
                        weatherResult.value = Result.Error(Exception(error))
                    }
                    .collect { weatherResult.value = Result.Success(it) }
            } else
                weatherResult.value =
                    Result.Error(IllegalArgumentException("Location cannot to be null"))
        }
    }
}