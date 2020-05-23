/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.util.WeatherConditionHelper
import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.net.json.JsonDailyData
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import java.text.SimpleDateFormat
import java.util.*

class DailyForecastMapper {

    companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }

    fun map(json: NetData<JsonDailyForecast>): DailyForecast {
        val dailyWeatherList = json.data.forecastList.mapIndexed { index, jsonDailyData ->
            jsonDailyData.toDailyWeather(index)
        }
        return DailyForecast(dailyWeatherList)
    }

    private fun JsonDailyData.toDailyWeather(index: Int): DailyForecast.DailyWeather {
        val ico = WeatherConditionHelper.getIconByCode(weather.icon)
        val dateFormatter = SimpleDateFormat(DATE_PATTERN, Locale.ROOT)
        val unixTimestamp = dateFormatter.parse(date)?.time ?: 0
        return DailyForecast.DailyWeather(maxTemp, minTemp, ico, index, unixTimestamp)
    }

}