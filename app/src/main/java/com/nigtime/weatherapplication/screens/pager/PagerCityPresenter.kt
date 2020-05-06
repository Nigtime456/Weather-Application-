/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screens.pager

import com.nigtime.weatherapplication.domain.city.ForecastCitiesRepository
import com.nigtime.weatherapplication.screens.common.BasePresenter
import com.nigtime.weatherapplication.common.rx.SchedulerProvider

class PagerCityPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastCitiesRepository: ForecastCitiesRepository
) : BasePresenter<PagerCityView>(
    schedulerProvider
) {
    private var currentPage = 0

    fun handlePagerPosition(page: Int) {
        currentPage = page
    }

    fun provideCities() {
        forecastCitiesRepository.getListCities()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(false) { list ->
                require(list.isNotEmpty()) { "pager screen must not receive empty cities list!" }
                getView()?.submitList(list)
                getView()?.setPage(currentPage)
            }
    }
}