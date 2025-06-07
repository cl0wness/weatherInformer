package com.app.weather.screens.weekWeather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.weather.R
import com.app.weather.ui.theme.DarkBlue
import com.app.weather.ui.theme.LightBlue
import com.app.weather.ui.theme.WeatherTheme
import com.app.weather.ui.theme.White

@Composable
fun WeekWeatherScreen(
    onBackClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LightBlue, DarkBlue)
                )
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Неделя, Москва",
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }
        }
        val list = listOf("Mon", "Tue")

        items(list) { day ->
            DayWeatherItem(day, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun DayWeatherItem(data: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(containerColor = White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = data,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(100.dp)
            )
            Column {
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = "💧 0%",
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = "💨 3 км/ч",
                )
            }
            Spacer(modifier = Modifier.width(20.dp))

            val weatherIcon: String? = "https://openweathermap.org/img/wn/02d@4x.png"
            weatherIcon?.let { icon ->
                Image(
                    painter = rememberAsyncImagePainter(model = icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "30° / 30°",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "description",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun WeekWeaterPreview() {
    WeatherTheme {
        WeekWeatherScreen()
    }
}