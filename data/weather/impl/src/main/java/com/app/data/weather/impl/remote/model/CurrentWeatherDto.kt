package com.app.data.weather.impl.remote.model

import com.app.data.weather.api.model.CurrentWeatherModel
import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.WeatherModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class CurrentWeatherResponse(
    @SerialName("data")
    val data: List<CurrentWeatherDto>
)

@Serializable
data class CurrentWeatherDto(
    @SerialName("temp")
    val temperature: Double,

    @SerialName("app_temp")
    val feelsLike: Double,

    @SerialName("rh")
    val humidity: Int,

    @SerialName("wind_spd")
    val windSpeed: Double,

    @SerialName("weather")
    val weather: WeatherDescriptionDto
)

@Serializable
data class WeatherDescriptionDto(
    @SerialName("description")
    val description: String,

    @SerialName("icon")
    val icon: String
)

fun CurrentWeatherDto.toDomain() : CurrentWeatherModel {
    return CurrentWeatherModel(
        temperature = temperature.toInt(),
        feelsLike = feelsLike.toInt(),
        humidity = humidity,
        windSpeed = windSpeed.toInt(),
        description = weather.description,
        iconUrl =  "https://www.weatherbit.io/static/img/icons/${weather.icon}.png"
    )
}