/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.gmail.nigtime456.weatherapplication.domain.repository

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocationsRepository {
    /**
     * Получить все добавленные города
     */
    fun getLocations(): Observable<List<SavedLocation>>

    fun hasLocations(): Single<Boolean>

    fun delete(item: SavedLocation): Completable

    fun replaceAll(items: List<SavedLocation>): Completable
}