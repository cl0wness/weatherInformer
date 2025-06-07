import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.WeatherModel
import com.app.data.weather.impl.remote.model.ConditionApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("forecast") val forecast: ForecastApiModel
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

fun ForecastDayApiModel.toDomain() : DailyForecast {
    return DailyForecast(
        date = date,
        weather = WeatherModel(
            tempMax = day.maxTempC.toInt(),
            tempMin = day.minTempC.toInt(),
            humidity = day.avgHumidity.toInt(),
            windSpeed = (day.maxWindKph / 3.6).toInt(),
            description = day.condition.text,
            iconUrl = "https:${day.condition.icon}"
        )
    )
}
