/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes

data class DailyForecast(val dailyWeather: List<DailyWeather>) {

    data class DailyWeather(
        val maxTemp: Double,
        val minTemp: Double,
        @DrawableRes val ico: Int,
        val index: Int,
        val unixTimestamp: Long
    )
}