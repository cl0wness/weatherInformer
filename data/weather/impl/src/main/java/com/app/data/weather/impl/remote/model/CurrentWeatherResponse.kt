package com.app.data.weather.impl.remote.model

import com.app.data.weather.api.model.CurrentWeatherModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    @SerialName("current") val current: CurrentApiModel,
    @SerialName("location") val location: LocationApiModel
)

@Serializable
data class CurrentApiModel(
    @SerialName("temp_c") val tempC: Double,
    @SerialName("feelslike_c") val feelsLikeC: Double,
    @SerialName("humidity") val humidity: Int,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("condition") val condition: ConditionApiModel
)

@Serializable
data class LocationApiModel(
    @SerialName("name") val name: String,
    @SerialName("localtime") val localTime: String
)

@Serializable
data class ConditionApiModel(
    @SerialName("text") val text: String,
    @SerialName("icon") val icon: String
)

fun CurrentWeatherResponse.toDomain() : CurrentWeatherModel {
    return CurrentWeatherModel(
        temperature = current.tempC.toInt(),
        feelsLike = current.feelsLikeC.toInt(),
        humidity = current.humidity,
        windSpeed = (current.windKph / 3.6).toInt(),
        description = current.condition.text,
        iconUrl =  "https:${current.condition.icon}",
        locationName = location.name
    )
}