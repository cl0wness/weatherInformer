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

fun CurrentApiModel.toDomain() : CurrentWeatherModel {
    return CurrentWeatherModel(
        temperature = tempC.toInt(),
        feelsLike = feelsLikeC.toInt(),
        humidity = humidity,
        windSpeed = (windKph / 3.6).toInt(),
        description = condition.text,
        iconUrl =  "https:${condition.icon}"
    )
}