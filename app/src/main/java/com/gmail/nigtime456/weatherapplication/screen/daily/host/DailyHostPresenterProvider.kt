/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.daily.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider

class DailyHostPresenterProvider(private val location: SavedLocation) :
    BasePresenterProvider<DailyHostPresenter>() {

    override fun createPresenter(): DailyHostPresenter {
        return DailyHostPresenter(location, appContainer.forecastProvider)
    }

    class Factory constructor(private val location: SavedLocation) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DailyHostPresenterProvider::class.java)) {
                return DailyHostPresenterProvider(location) as T
            } else {
                error("unknown view model class")
            }
        }
    }


}