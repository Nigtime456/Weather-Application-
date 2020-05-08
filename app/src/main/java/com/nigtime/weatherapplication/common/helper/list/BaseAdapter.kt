/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.common.helper.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> constructor(diffCallback: DiffUtil.ItemCallback<T>? = null) :
    RecyclerView.Adapter<VH>() {

    private var list = emptyList<T>()
    private var differ: AsyncListDiffer<T>? = null


    init {
        diffCallback?.let {
            differ = AsyncListDiffer(this, diffCallback)
        }
    }

    fun submitList(newList: List<T>) {
        list = newList
        differ?.submitList(newList) ?: notifyDataSetChanged()
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return createViewHolder(inflater, parent, viewType)
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        bindViewHolder(holder, position, getItem(position))
    }

    final override fun getItemCount(): Int = list.size

    fun getItem(position: Int): T = list[position]

    abstract fun bindViewHolder(holder: VH, position: Int, item: T)

    abstract fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VH

}