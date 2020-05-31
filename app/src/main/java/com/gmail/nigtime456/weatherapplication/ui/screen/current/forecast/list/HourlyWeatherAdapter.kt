/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyWeather
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.ui.list.BaseAdapter
import com.gmail.nigtime456.weatherapplication.ui.list.SimpleDiffCallback

class HourlyWeatherAdapter(rxAsyncDiffer: RxAsyncDiffer) :
    BaseAdapter<HourlyWeather, HourlyWeatherViewHolder>(DIFF_CALLBACK, rxAsyncDiffer) {

    companion object {
        val DIFF_CALLBACK = object : SimpleDiffCallback<HourlyWeather>() {
            override fun areItemsTheSame(
                old: HourlyWeather,
                new: HourlyWeather
            ): Boolean {
                return old.dateTime == new.dateTime
            }

            override fun areContentsTheSame(
                old: HourlyWeather,
                new: HourlyWeather
            ): Boolean {
                return old == new
            }
        }
    }

    private lateinit var unitOfTemp: UnitOfTemp

    fun submitList(newList: List<HourlyWeather>, unitOfTemp: UnitOfTemp) {
        val calculateDiffs = this::unitOfTemp.isInitialized && this.unitOfTemp == unitOfTemp
        this.unitOfTemp = unitOfTemp
        submitList(newList, calculateDiffs)
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): HourlyWeatherViewHolder {
        val view = inflater.inflate(R.layout.item_hourly_forecast, parent, false)
        return HourlyWeatherViewHolder(view)
    }

    override fun bindViewHolder(
        holder: HourlyWeatherViewHolder,
        position: Int,
        item: HourlyWeather
    ) {
        holder.bindItem(item, unitOfTemp)
    }

}