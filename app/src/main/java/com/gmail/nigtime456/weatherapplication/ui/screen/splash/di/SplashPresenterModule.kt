/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.splash.di

import com.gmail.nigtime456.weatherapplication.ui.screen.splash.SplashContract
import com.gmail.nigtime456.weatherapplication.ui.screen.splash.SplashPresenter
import dagger.Binds
import dagger.Module

@Module
interface SplashPresenterModule {
    @Binds
    fun providePresenter(presenter: SplashPresenter): SplashContract.Presenter
}