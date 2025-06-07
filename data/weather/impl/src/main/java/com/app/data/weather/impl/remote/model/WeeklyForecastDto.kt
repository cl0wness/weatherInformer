import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.WeatherModel
import com.app.data.weather.impl.remote.model.WeatherDescriptionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyForecastResponse(
    @SerialName("data")
    val data: List<DailyForecastDto>
)

@Serializable
data class DailyForecastDto(
    @SerialName("valid_date")
    val date: String,

    @SerialName("temp")
    val avgTemp: Double,

    @SerialName("max_temp")
    val maxTemp: Double,

    @SerialName("min_temp")
    val minTemp: Double,

    @SerialName("rh")
    val humidity: Int,

    @SerialName("wind_spd")
    val windSpeed: Double,

    @SerialName("weather")
    val weather: WeatherDescriptionDto
)

fun DailyForecastDto.toDomain() : DailyForecast {
    return DailyForecast(
        date = date,
        weather = WeatherModel(
            tempMax = maxTemp.toInt(),
            tempMin = minTemp.toInt(),
            humidity = humidity,
            windSpeed = windSpeed.toInt(),
            description = weather.description,
            iconUrl = "https://www.weatherbit.io/static/img/icons/${weather.icon}.png"
        )
    )
}
