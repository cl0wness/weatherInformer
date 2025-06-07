package com.app.data.weather.api.model

/**
 * Модель погодных данных.
 * @param tempMin минимальная температура за день.
 * @param tempMax максимальная температура за день.
 * @param humidity влажность воздуха в процентах.
 * @param windSpeed скорость ветра.
 * @param description текстовое описание погодных условий (опционально).
 * @param iconUrl URL иконки погоды (опционально).
 */
data class WeatherModel (
    val tempMin: Int,
    val tempMax: Int,
    val humidity: Int,
    val windSpeed: Int,
    val description: String? = null,
    val iconUrl: String? = null
)