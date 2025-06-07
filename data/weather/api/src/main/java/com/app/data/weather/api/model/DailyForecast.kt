package com.app.data.weather.api.model

import java.time.LocalDate
import java.util.Date

/**
 * Модель прогноза погоды на один день.
 * @param date дата прогноза в формате строки (например, "2025-06-07").
 * @param weather подробная информация о погоде в этот день.
 */
data class DailyForecast(
    val date: String,
    val locationName: String,
    val weather: WeatherModel
)