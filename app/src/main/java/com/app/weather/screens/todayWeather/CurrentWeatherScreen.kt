package com.app.weather.screens.todayWeather

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import com.app.weather.R
import com.app.weather.screens.event.ScreenEvent
import com.app.weather.screens.event.UIEvent
import com.app.weather.ui.theme.DarkBlue
import com.app.weather.ui.theme.LightBlue
import com.app.weather.ui.theme.White
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CurrentWeatherScreen(
    onWeeklyClick: () -> Unit = {}
) {
    val viewModel = koinViewModel<CurrentWeatherViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Слушатель одноразовый событий от вью-модели
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

    // Лаунчер для открытия настроек приложения
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}

    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // При первом входе сразу запускаем запрос разрешения
    val requestedPermission = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!requestedPermission.value && !permissionState.status.isGranted) {
            requestedPermission.value = true
            permissionState.launchPermissionRequest()
        }
    }
    // При получении разрешения в процессе, обновляем экран
    val lastStatus = rememberSaveable { mutableStateOf(permissionState.status.isGranted) }
    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted != lastStatus.value) {
            lastStatus.value = permissionState.status.isGranted
            viewModel.onEvent(ScreenEvent.Refresh)
        }
    }

    // Контент
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LightBlue, DarkBlue)
                )
            )
            .padding(WindowInsets.Companion.systemBars.asPaddingValues())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.weather_now),
                style = MaterialTheme.typography.titleLarge,
                color = White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = state.weather?.locationName.orEmpty(),
                style = MaterialTheme.typography.titleLarge,
                color = White
            )

            Spacer(Modifier.height(20.dp))

            if (state.isLoading) {
                // Загрузка
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 100.dp),
                    color = White
                )
            } else {
                state.weather?.let { weather ->
                    weather.iconUrl?.let { icon ->
                        Image(
                            painter = rememberAsyncImagePainter(model = icon),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "${weather.temperature}°C",
                        style = MaterialTheme.typography.displayLarge,
                        color = White
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = weather.description.orEmpty()
                            .replaceFirstChar(Char::uppercaseChar),
                        style = MaterialTheme.typography.titleMedium,
                        color = White
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(R.string.feels_like, weather.feelsLike.toInt()),
                        color = White.copy(alpha = 0.8f)
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = stringResource(R.string.humidity_full, weather.humidity),
                            color = White
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = stringResource(R.string.wind_full, weather.windSpeed),
                            color = White
                        )
                    }
                } ?: Text(
                    text = stringResource(R.string.couldn_t_upload_data),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }
        }


        // Разрешение отклонено с "не спрашивать"
        if (!permissionState.status.shouldShowRationale && !permissionState.status.isGranted) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    stringResource(R.string.for_full_app_work_need_permision_to_location),
                    color = White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        settingsLauncher.launch(intent)
                    }
                ) {
                    Text(stringResource(R.string.open_settings))
                }
            }

        }

        Button(
            onClick = onWeeklyClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 12.dp),
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