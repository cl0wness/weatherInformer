package com.app.data.weather.api.model

data class ForecastModel(
    val locationName: String,
    val weather: List<DailyForecast>
)