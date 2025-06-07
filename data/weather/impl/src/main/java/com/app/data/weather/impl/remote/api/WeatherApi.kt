package com.app.data.weather.impl.remote.api

import WeeklyForecastResponse
import com.app.data.weather.impl.remote.model.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "M"
    ): Response<CurrentWeatherResponse>

    @GET("forecast/daily")
    suspend fun getWeeklyForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("days") days: Int = 7,
        @Query("units") units: String = "M"
    ): Response<WeeklyForecastResponse>
}