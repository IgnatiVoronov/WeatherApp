package com.example.weatherapp.di

import com.example.weatherapp.domain.GetUiWeatherModelUseCase
import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.model.WeatherRepositoryImpl
import com.example.weatherapp.model.network.WeatherApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = "https://api.openweathermap.org"

    @Singleton
    @Provides
    fun provideRetrofit(base_url: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url)
            .build()
    }

    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiInterface {
        return retrofit.create(WeatherApiInterface::class.java)
    }

    @Provides
    fun provideWeatherRepository(api: WeatherApiInterface): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    fun provideGetUiWeatherModelUsecase(repo: WeatherRepository): GetUiWeatherModelUseCase {
        return GetUiWeatherModelUseCase(repo)
    }

}