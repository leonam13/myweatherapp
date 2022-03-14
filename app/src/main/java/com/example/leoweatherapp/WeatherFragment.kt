package com.example.leoweatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.leoweatherapp.data.Result
import com.example.leoweatherapp.data.model.WeatherDetail
import com.example.leoweatherapp.databinding.FragmentWeatherBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : LocationServicesFragment() {

    private val viewModel: WeatherViewModel by viewModels()

    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
        get() = _binding!!

    override var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d(javaClass.simpleName, "Location: ${locationResult.lastLocation}")
            setLoading(false)
            viewModel.getWeather(locationResult.lastLocation)
            removeLocationUpdates()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentWeatherBinding.inflate(inflater).let {
            _binding = it
            it.root
        }
    }

    override fun onStart() {
        super.onStart()
        setLoading(true)
        initRequireLocationFlow()
    }

    override fun onStop() {
        super.onStop()
        removeLocationUpdates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> setLoading(true)
                is Result.Error -> {
                    setLoading(false)
                    findNavController().navigate(WeatherFragmentDirections.weatherToError(result.exception))
                }
                is Result.Success -> {
                    setLoading(false)
                    bindData(result)
                }
            }
        }
    }

    private fun setLoading(showLoading: Boolean) {
        (requireActivity() as? MainActivity)?.setLoading(showLoading)
    }

    private fun bindData(result: Result.Success<WeatherDetail>) {
        binding.apply {
            val weatherDetail = result.data
            val weatherDetailTemperature = result.data.temperature.first()
            Glide.with(this@WeatherFragment).load(weatherDetailTemperature.stateIcon).into(stateIv)
            cityTv.text = weatherDetail.city
            currentTempTv.text = getString(
                R.string.weather_fragment_current_temperature,
                weatherDetailTemperature.current
            )
            stateTv.text = weatherDetailTemperature.state
            minMaxTv.text = getString(
                R.string.weather_fragment_min_max_temperature,
                weatherDetailTemperature.min,
                weatherDetailTemperature.max
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}