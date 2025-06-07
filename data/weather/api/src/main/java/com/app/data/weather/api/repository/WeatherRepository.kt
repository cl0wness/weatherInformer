package com.app.data.weather.api.repository

import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.model.CurrentWeatherModel
import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.ForecastModel

/**
 * Репозиторий для получения данных о погоде.
 */
interface WeatherRepository {

    /** Получение текущей погоды по координатам (широта, долгота). */
    suspend fun fetchCurrentWeather(lat: Double, lon: Double): Resource<CurrentWeatherModel, String>

    /** Получение прогноза погоды на неделю по координатам (широта, долгота). */
    suspend fun fetchWeeklyWeather(lat: Double, lon: Double) : Resource<ForecastModel, String>
}