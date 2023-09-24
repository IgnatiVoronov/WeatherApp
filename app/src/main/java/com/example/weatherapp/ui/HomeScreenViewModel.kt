package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.GetUiWeatherModelUseCase
import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.model.data.WeatherModelDTO
import com.example.weatherapp.model.data.ui.WeatherInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class HomeScreenViewModel @Inject constructor(
    private val weatherModelUseCase: GetUiWeatherModelUseCase
) : ViewModel() {

    private val _locationLatitude: MutableLiveData<Double?> = MutableLiveData(null)
    val locationLatitude: LiveData<Double?> = _locationLatitude

    private val _locationLongitude: MutableLiveData<Double?> = MutableLiveData(null)
    val locationLongitude: LiveData<Double?> = _locationLongitude

    private val _weatherFlow: MutableStateFlow<WeatherInfo?> = MutableStateFlow(null)
    val weatherFlow: StateFlow<WeatherInfo?> = _weatherFlow

    var loadWeatherPollingJob: Job? = null

    private fun loadWeather() {
        viewModelScope.launch {
            val weatherModel =
                weatherModelUseCase.getUiWeatherModel(locationLatitude.value.toString(), locationLatitude.value.toString())

            _weatherFlow.emit(weatherModel)
        }
    }

    fun launchLongPolling() {
        loadWeatherPollingJob?.cancel()
        loadWeatherPollingJob = CoroutineScope(Dispatchers.IO).launch {
            loadWeather()
        }
    }

    fun stopPolling() {
        loadWeatherPollingJob?.cancel()
    }

    fun setLocation(location: Location){
        _locationLatitude.value = location.latitude
        _locationLongitude.value = location.longitude
    }

}