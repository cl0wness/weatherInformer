package com.app.data.weather.api.model

/**
 * Модель данных для текущей погоды.
 *
 * @param temperature Температура (°C)
 * @param feelsLike Температура по ощущению (°C)
 * @param humidity Влажность (%)
 * @param windSpeed Скорость ветра (м/с)
 * @param description Описание погоды (например, "ясно")
 * @param iconUrl URL иконки погоды
 */
data class CurrentWeatherModel(
    val temperature: Int,
    val feelsLike: Int,
    val humidity: Int,
    val windSpeed: Int,
    val description: String? = null,
    val iconUrl: String? = null
)