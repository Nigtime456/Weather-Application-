/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.daily.host

import android.util.Log
import com.gmail.nigtime456.weatherapplication.common.util.DateFormatFactory
import com.gmail.nigtime456.weatherapplication.domain.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter
import io.reactivex.rxkotlin.subscribeBy

class DailyHostPresenter constructor(
    private val currentLocation: SavedLocation,
    private val forecastProvider: ForecastProvider
) : BasePresenter<DailyHostView>(TAG) {

    companion object {
        const val TAG = "daily_pages"
    }

    private val weekDayFormatter = DateFormatFactory.getShortWeekdayWithDayFormatter()
    private var currentPage: Int = 0

    override fun onAttach() {
        super.onAttach()
        init()
    }

    private fun init() {
        getView()?.setLocation(currentLocation.getName())
        provideForecast(false)
    }

    fun setCurrentPage(page: Int) {
        currentPage = page
    }

    fun onRequestRefresh() {
        provideForecast(true)
    }

    private fun provideForecast(forceNet: Boolean) {
        startMeasure()
        forecastProvider.getDailyForecast(currentLocation.createRequestParams(), forceNet)
            .map(this::mapDailyWeatherToDateList)
            .doFinally { getView()?.stopRefreshing() }
            .subscribeBy(onNext = this::onResult, onError = this::onError)
            .disposeOnDestroy()
    }

    private fun mapDailyWeatherToDateList(dailyForecast: DailyForecast): List<String> {
        return dailyForecast.weather.map { item ->
            weekDayFormatter.format(item.dateTime)
        }
    }

    private fun onResult(titles: List<String>) {
        Log.d("sas", "DAILY FORECAST LOADED = [${endMeasure()}]")
        getView()?.setViewPager(currentLocation)
        getView()?.setTabLayout(titles)
        getView()?.setPage(currentPage)
    }

    private fun onError(throwable: Throwable) {
        logger.e(throwable)
        getView()?.showErrorMessage()
    }

    fun onNavigationClick() {
        getView()?.navigateToPreviousScreen()
    }


}