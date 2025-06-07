package com.app.data.weather.impl.remote.api

import ForecastResponse
import com.app.data.weather.impl.remote.model.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") lang: String = "ru"
    ): Response<CurrentWeatherResponse>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") days: Int = 7,
        @Query("lang") lang: String = "ru"
    ): Response<ForecastResponse>
}