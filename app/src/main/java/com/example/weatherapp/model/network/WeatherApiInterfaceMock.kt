package com.example.weatherapp.model.network

import com.example.weatherapp.model.data.WeatherModelDTO
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class WeatherApiInterfaceMock : WeatherApiInterface {
    override suspend fun getWeatherByLocation(
        lat: String,
        lon: String,
        appId: String
    ): Response<WeatherModelDTO> {
        return Response.success(null)
    }
}