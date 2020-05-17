/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash


import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.location.SavedLocationsRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter

class SplashPresenter(
    schedulerProvider: SchedulerProvider,
    private val savedLocationsRepository: SavedLocationsRepository
) :
    BasePresenter<SplashView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "splash"
    }

    override fun onAttach() {
        super.onAttach()
        dispatchScreen()
    }

    private fun dispatchScreen() {
        savedLocationsRepository.hasLocations()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(onResult = this::dispatchResult)
    }

    private fun dispatchResult(hasCities: Boolean) {
        getView()?.finishSplash()
        if (hasCities) {
            getView()?.navigateToLocationPagesScreen()
        } else {
            getView()?.navigateToSavedLocationsScreen()
        }
    }
}