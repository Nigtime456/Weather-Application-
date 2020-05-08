/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.screen.search.paging

import android.util.Log
import android.view.*
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.helper.ColorSpanHelper
import com.nigtime.weatherapplication.domain.city.SearchCity
import kotlinx.android.synthetic.main.item_search_city.view.*

/**
 * Адаптер для подгружаемого списка
 */
class PagedSearchAdapter constructor(private val spannHelper: ColorSpanHelper) :
    PagedListAdapter<SearchCity, PagedSearchAdapter.CityViewHolder>(DIFF_CALLBACK) {

    /**
     * Оптимизированная обработка кликов: не создается OnClickListener на каждый элемент
     */
    class ItemClickClickListener constructor(
        private val recyclerView: RecyclerView,
        private val onClick: (SearchCity) -> Unit
    ) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        private val simpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }
        }

        init {
            recyclerView.addOnItemTouchListener(this)
            gestureDetector = GestureDetector(recyclerView.context, simpleOnGestureListener)
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            child?.let {
                child.performClick()
                if (gestureDetector.onTouchEvent(e)) {
                    val viewHolder = recyclerView.getChildViewHolder(child)
                    val cityViewHolder = viewHolder as CityViewHolder
                    onClick(cityViewHolder.getCurrentCity())
                }
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            //nothing
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            //nothing
        }
    }


    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentCity: SearchCity? = null

        fun bind(city: SearchCity, spannHelper: ColorSpanHelper) {
            currentCity = city
            itemView.itemSearchName.text = spannHelper.highlightText(city.name, city.query)
            itemView.itemSearchStateAndCountry.text = city.getStateAndCounty()
            //выключаем, что отключить рипл эффект
            itemView.isEnabled = !city.isWish
            Log.d("sas", "city = ${city.name} isWish = ${city.isWish}")
        }

        fun release() {
            currentCity = null
        }

        fun getCurrentCity(): SearchCity {
            return currentCity ?: error("currentCity == null")
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchCity>() {
            override fun areItemsTheSame(
                oldItem: SearchCity,
                newItem: SearchCity
            ): Boolean {
                return oldItem.cityId == newItem.cityId
            }

            override fun areContentsTheSame(
                oldItem: SearchCity,
                newItem: SearchCity
            ): Boolean {
                return oldItem.cityId == newItem.cityId && oldItem.query == newItem.query
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CityViewHolder(inflater.inflate(R.layout.item_search_city, parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val searchCityData = getItem(position)
        searchCityData?.let { holder.bind(it, spannHelper) }
    }

    override fun onViewRecycled(holder: CityViewHolder) {
        super.onViewRecycled(holder)
        holder.release()
    }

}