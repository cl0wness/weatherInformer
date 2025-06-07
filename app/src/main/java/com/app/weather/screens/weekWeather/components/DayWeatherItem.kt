package com.app.weather.screens.weekWeather.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.data.weather.api.model.DailyForecast
import com.app.weather.R
import com.app.weather.ui.theme.White
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun DayWeatherItem(data: DailyForecast, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(containerColor = White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = formatDate(data.date),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(100.dp)
            )
            Column {
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = stringResource(R.string.humidity, data.weather.humidity),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = stringResource(R.string.wind, data.weather.windSpeed),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            data.weather.iconUrl?.let { icon ->
                Image(
                    painter = rememberAsyncImagePainter(model = icon),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }

            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "${data.weather.tempMax}° / ${data.weather.tempMin}°",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = data.weather.description.orEmpty()
                        .replaceFirstChar(Char::uppercaseChar),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

fun formatDate(dateString: String): String {
    val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.parse(dateString)
    } else {
        // Строку, как было
        return dateString
    } // формат yyyy-MM-dd

    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru"))
    val day = date.dayOfMonth
    val month = date.month.getDisplayName(TextStyle.SHORT, Locale("ru"))

    return "$dayOfWeek, $day $month"
}