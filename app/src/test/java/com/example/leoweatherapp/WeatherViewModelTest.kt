package com.example.leoweatherapp

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.leoweatherapp.data.Result
import com.example.leoweatherapp.data.repository.WeatherRepository
import getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherRepository = WeatherMockRepository()
    }

    @Test
    fun getWeather_locationNotNull_returnSuccess() {
        val viewModel = WeatherViewModel(weatherRepository)

        val location = Location("fused -23.599317,-46.577808")

        viewModel.getWeather(location)

        val value = viewModel.weatherResult.getOrAwaitValue()

        assertThat(value, instanceOf(Result.Success::class.java))
    }

    @Test
    fun getWeather_locationNotNull_returnValidData() {
        val viewModel = WeatherViewModel(weatherRepository)

        val location = Location("fused -23.599317,-46.577808")

        viewModel.getWeather(location)

        val value = viewModel.weatherResult.getOrAwaitValue()

        assertThat(value, instanceOf(Result.Success::class.java))

        val data = (value as Result.Success).data

        assertThat(data.city, `is`(not("")))
        assertThat(data.city, `is`(notNullValue()))
        assertThat(data.temperature.size, greaterThan(0))

        val tempDetail = data.temperature.first()
        assertThat(tempDetail.state, `is`(not("")))
        assertThat(tempDetail.state, `is`(notNullValue()))
        assertThat(tempDetail.stateIcon, `is`(not("")))
        assertThat(tempDetail.stateIcon, `is`(notNullValue()))
    }

    @Test
    fun getWeather_locationNull_returnError() {
        val viewModel = WeatherViewModel(weatherRepository)

        viewModel.getWeather(null)

        val value = viewModel.weatherResult.getOrAwaitValue()

        assertThat(value, instanceOf(Result.Error::class.java))
    }
}