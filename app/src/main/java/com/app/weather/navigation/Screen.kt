package com.app.weather.navigation

/**
 * Класс для определения экранов навигации с их маршрутами.
 *
 * @property route Строка маршрута для навигации.
 */
sealed class Screen(val route: String) {
    /**
     * Экран с погодой на сегодня.
     * Используется как начальный маршрут.
     */
    object Today : Screen("today")

    /**
     * Экран с прогнозом погоды на неделю.
     */
    object Week : Screen("week")
}