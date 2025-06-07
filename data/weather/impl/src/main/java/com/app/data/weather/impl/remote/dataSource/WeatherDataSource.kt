package com.app.data.weather.impl.remote.dataSource

import ForecastResponse
import com.app.data.weather.api.core.Resource
import com.app.data.weather.impl.remote.api.WeatherApi
import com.app.data.weather.impl.remote.dataSource.core.getResult
import com.app.data.weather.impl.remote.model.CurrentWeatherResponse

class WeatherDataSource(
    private val api: WeatherApi
) {
    suspend fun fetchCurrentWeather(lat: Double, lon: Double): Resource<CurrentWeatherResponse, String> {
        return getResult<CurrentWeatherResponse> {
            api.getCurrentWeather("$lat,$lon")
        }
    }

    suspend fun fetchWeekWeather(lat: Double, lon: Double): Resource<ForecastResponse, String> {
        return getResult<ForecastResponse> {
            api.getForecast("$lat,$lon")
        }
    }
}