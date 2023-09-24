package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.GetUiWeatherModelUseCase
import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.model.data.WeatherModelDTO
import com.example.weatherapp.model.data.ui.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class HomeScreenViewModel @Inject constructor(
    private val weatherModelUseCase: GetUiWeatherModelUseCase
) : ViewModel() {

    private val _weatherFlow: MutableStateFlow<WeatherInfo?> = MutableStateFlow(null)
    val weatherFlow: StateFlow<WeatherInfo?> = _weatherFlow

    var loadWeatherPollingJob: Job? = null

    private fun loadWeather() {
        viewModelScope.launch {
            val weatherModel = weatherModelUseCase.getUiWeatherModel("27", "53")

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
}