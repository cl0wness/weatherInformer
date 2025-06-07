import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.ForecastModel
import com.app.data.weather.api.model.WeatherModel
import com.app.data.weather.impl.remote.model.ConditionApiModel
import com.app.data.weather.impl.remote.model.LocationApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("forecast") val forecast: ForecastApiModel,
    @SerialName("location") val location: LocationApiModel
)

@Serializable
data class ForecastApiModel(
    @SerialName("forecastday") val forecastDay: List<ForecastDayApiModel>
)

@Serializable
data class ForecastDayApiModel(
    @SerialName("date") val date: String,
    @SerialName("day") val day: DayWeatherApiModel
)

@Serializable
data class DayWeatherApiModel(
    @SerialName("maxtemp_c") val maxTempC: Double,
    @SerialName("mintemp_c") val minTempC: Double,
    @SerialName("avgtemp_c") val avgTempC: Double,
    @SerialName("maxwind_kph") val maxWindKph: Double,
    @SerialName("avghumidity") val avgHumidity: Double,
    @SerialName("condition") val condition: ConditionApiModel
)

fun ForecastResponse.toDomain() : ForecastModel {
    return ForecastModel(
        locationName = location.name,
        weather = forecast.forecastDay.map { item ->
            DailyForecast(
                date = item.date,
                locationName = "",
                weather = WeatherModel(
                    tempMax = item.day.maxTempC.toInt(),
                    tempMin = item.day.minTempC.toInt(),
                    humidity = item.day.avgHumidity.toInt(),
                    windSpeed = (item.day.maxWindKph / 3.6).toInt(),
                    description = item.day.condition.text,
                    iconUrl = "https:${item.day.condition.icon}",
                )
            )
        }
    )
}
