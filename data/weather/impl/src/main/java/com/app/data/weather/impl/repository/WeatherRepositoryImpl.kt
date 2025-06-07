package com.app.data.weather.impl.repository

import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.model.CurrentWeatherModel
import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.repository.WeatherRepository
import com.app.data.weather.impl.remote.dataSource.WeatherDataSource
import com.app.data.weather.impl.remote.model.toDomain
import toDomain

class WeatherRepositoryImpl (
    private val dataSource: WeatherDataSource
) : WeatherRepository {
    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double
    ): Resource<CurrentWeatherModel, String> {
        return dataSource.fetchCurrentWeather(lat = lat, lon = lon)
            .mapData { it.current.toDomain()}
    }

    override suspend fun fetchWeeklyWeather(
        lat: Double,
        lon: Double
    ): Resource<List<DailyForecast>, String> {
        return dataSource.fetchWeekWeather(lat = lat, lon = lon).mapData { response ->
            response.forecast.forecastDay.map { it.toDomain() }
        }
    }
}