/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.currentforecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class CurrentForecastPresenterProvider(private val location: SavedLocation) :
    BasePresenterProvider<CurrentForecastPresenter>() {

    companion object {
        private val daysSwitchSubject: Subject<Int> = BehaviorSubject.create()
        private val verticalScrollSubject: Subject<Int> = BehaviorSubject.create()
    }

    override fun createPresenter(): CurrentForecastPresenter {
        return CurrentForecastPresenter(
            appContainer.schedulerProvider,
            location,
            appContainer.forecastManager,
            appContainer.settingsManager,
            daysSwitchSubject,
            verticalScrollSubject
        )
    }


    class Factory(private val location: SavedLocation) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CurrentForecastPresenterProvider::class.java)) {
                return CurrentForecastPresenterProvider(location) as T
            } else {
                error("unknown view model class")
            }
        }
    }
}