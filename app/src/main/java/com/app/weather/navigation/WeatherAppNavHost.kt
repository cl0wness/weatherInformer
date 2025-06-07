package com.app.weather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.weather.screens.todayWeather.CurrentWeatherScreen
import com.app.weather.screens.weekWeather.WeeklyWeatherScreen

@Composable
fun WeatherAppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Today.route) {


        composable(Screen.Today.route) {
            CurrentWeatherScreen(
                onWeeklyClick = { navController.navigate(Screen.Week.route) }
            )
        }

        composable(Screen.Week.route) {
            WeeklyWeatherScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}