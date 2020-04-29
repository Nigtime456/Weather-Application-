/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.domain.cities.CityForForecast
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.domain.repository.cities.ForecastCitiesRepository
import io.reactivex.Single

class ForecastCitiesRepositoryImpl constructor(
    private val referenceCityDao: ReferenceCityDao,
    private val wishCityDao: WishCityDao
) : ForecastCitiesRepository {

    override fun getListCities(): Single<List<CityForForecast>> {
        return Single.fromCallable { wishCityDao.getAllIds() }
            .map { ids -> ids.map(this::mapCity) }
    }

    //TODO mapper
    private fun mapCity(cityId: Long): CityForForecast {
        return CityForForecast(cityId, referenceCityDao.getNameById(cityId))
    }
}