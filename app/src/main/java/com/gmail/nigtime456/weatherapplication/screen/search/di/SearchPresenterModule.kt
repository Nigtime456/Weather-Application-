/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.search.di

import com.gmail.nigtime456.weatherapplication.screen.search.SearchContract
import com.gmail.nigtime456.weatherapplication.screen.search.SearchPresenter
import dagger.Binds
import dagger.Module

@Module
interface SearchPresenterModule {
    @Binds
    fun providePresenter(presenter: SearchPresenter): SearchContract.Presenter
}