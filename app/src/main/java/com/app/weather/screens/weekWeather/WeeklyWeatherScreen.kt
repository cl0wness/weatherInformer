package com.app.weather.screens.weekWeather

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import com.app.data.weather.api.model.DailyForecast
import com.app.weather.R
import com.app.weather.screens.event.UIEvent
import com.app.weather.ui.theme.DarkBlue
import com.app.weather.ui.theme.LightBlue
import com.app.weather.ui.theme.White
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeeklyWeatherScreen(
    onBackClick: () -> Unit = {}
) {
    val viewModel = koinViewModel<WeeklyWeatherViewModel>()
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvents.collectLatest { event ->
                when (event) {
                    is UIEvent.Error -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LightBlue, DarkBlue)
                )
            ),
        contentPadding = WindowInsets.Companion.systemBars.asPaddingValues(),
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
                    text = stringResource(R.string.weather_on_week),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }
        }

        if (state.isLoading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 100.dp),
                    color = White
                )
            }
        } else {
            state.weekWeather?.let {
                items(it) { day ->
                    DayWeatherItem(day, modifier = Modifier.padding(horizontal = 16.dp))
                }
            } ?: item {
                Text(
                    text = stringResource(R.string.couldn_t_upload_data),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }
        }
    }
}

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
                text = data.date.toString(),
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
            Spacer(modifier = Modifier.width(20.dp))

            data.weather.iconUrl?.let { icon ->
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