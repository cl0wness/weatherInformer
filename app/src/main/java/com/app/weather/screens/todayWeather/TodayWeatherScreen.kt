package com.app.weather.screens.todayWeather

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.weather.ui.theme.DarkBlue
import com.app.weather.ui.theme.LightBlue
import com.app.weather.ui.theme.WeatherTheme
import com.app.weather.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayWeatherScreen(
    onWeeklyClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LightBlue, DarkBlue)
                )
            )
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(32.dp))

                Text(
                    text = "Сегодня, Москва",
                    style = MaterialTheme.typography.titleLarge,
                    color = White
                )

                Spacer(Modifier.height(20.dp))

                val weatherIcon: String? = "https://openweathermap.org/img/wn/02d@4x.png"
                weatherIcon?.let { icon ->
                    Image(
                        painter = rememberAsyncImagePainter(model = icon),
                        contentDescription = null,
                        modifier = Modifier.size(96.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "23°C",
                    style = MaterialTheme.typography.displayLarge,
                    color = White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "description".orEmpty().replaceFirstChar(Char::uppercaseChar),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "30° / 30°",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = "Ощущается как 30°",
                    color = White.copy(alpha = 0.8f)
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "💧 Влажность: 0%",
                        color = White
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "💨 Ветер: 3 км/ч",
                        color = White
                    )
                }
            }

            Button(
                onClick = onWeeklyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = White)
            ) {
                Text(
                    "Погода на неделю",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayWeaterScreenPreview() {
    WeatherTheme {
        TodayWeatherScreen()
    }
}