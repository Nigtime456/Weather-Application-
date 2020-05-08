/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.helper.ThemeHelper
import com.nigtime.weatherapplication.common.helper.list.ColorDividerDecoration
import com.nigtime.weatherapplication.common.helper.list.ItemTouchController
import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterFactory
import com.nigtime.weatherapplication.screen.common.Screen
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import kotlinx.android.synthetic.main.fragmet_wish_list.*


class WishCitiesFragment :
    BaseFragment<WishCitiesView, WishCitiesPresenter, NavigationController>(R.layout.fragmet_wish_list),
    WishCitiesView, SearchCityFragment.TargetFragment {


    /**
     * Фрагмент, который
     */
    interface TargetFragment {
        fun onSelectCity(position: Int)
    }


    private var itemTouchHelper: ItemTouchHelper? = null
    private var undoSnackbar: Snackbar? = null
    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        wishRecycler?.let { recyclerView ->
            val scrollOffset = recyclerView.computeVerticalScrollOffset()
            wishAppbar.isSelected = scrollOffset > 0
        }
    }


    private val adapterListener = object : WishCitiesListAdapter.Listener {
        override fun onRequestDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper?.startDrag(viewHolder)
        }

        override fun onItemSwiped(
            item: WishCity,
            position: Int,
            items: MutableList<WishCity>
        ) {
            presenter.onItemSwiped(item, position, items)
        }

        override fun onItemsMoved(items: List<WishCity>) {
            presenter.onItemsMoved(items)
        }

        override fun onItemClick(position: Int) {
            presenter.onClickItem(position)
        }

    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterFactory(): PresenterFactory<WishCitiesPresenter> {
        return ViewModelProvider(this).get(WishCitiesPresenterFactory::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.provideCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        itemTouchHelper = null
    }

    override fun onStart() {
        super.onStart()
        wishRecycler.viewTreeObserver.addOnScrollChangedListener(liftScrollListener)
    }


    override fun onStop() {
        super.onStop()
        wishRecycler.viewTreeObserver.removeOnScrollChangedListener(liftScrollListener)
        presenter.onViewStop()
    }

    private fun initViews() {
        setupRecycler()
        setupAppBar()
    }

    private fun setupAppBar() {
        wishToolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menuAdd) {
                    presenter.onClickMenuAdd()
                }
                true
            }
            setNavigationOnClickListener {
                presenter.onClickNavigationButton()
            }
        }

    }

    private fun setupRecycler() {
        wishRecycler.apply {
            val listAdapter = WishCitiesListAdapter(adapterListener)
            adapter = listAdapter
            initTouchGestures(listAdapter)
            addItemDecoration(getDivider())
            itemTouchHelper?.attachToRecyclerView(this)
        }
    }

    private fun initTouchGestures(listAdapter: WishCitiesListAdapter) {
        val itemTouchController = ItemTouchController(listAdapter)
        itemTouchHelper = ItemTouchHelper(itemTouchController)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themeDividerColor)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    override fun submitList(items: List<WishCity>) {
        (wishRecycler.adapter as WishCitiesListAdapter).submitList(items)
    }

    override fun showProgressLayout() {
        wishViewSwitcher.switchTo(0, false)
    }

    override fun showListLayout() {
        wishViewSwitcher.switchTo(1, false)

    }

    override fun showEmptyLayout() {
        wishViewSwitcher.switchTo(2, true)
    }

    override fun insertItemToList(item: WishCity, position: Int) {
        (wishRecycler.adapter as WishCitiesListAdapter).insertItemToList(item, position)
    }

    override fun scrollToPosition(position: Int) {
        wishRecycler.smoothScrollToPosition(position)
    }

    override fun delayScrollToPosition(position: Int) {
        wishRecycler.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                wishRecycler.smoothScrollToPosition(position)
                wishRecycler.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    override fun showUndoDeleteSnack(durationMillis: Int) {
        undoSnackbar = Snackbar.make(wishRoot, R.string.wish_city_removed, durationMillis).apply {
            setAction(R.string.wish_undo) { presenter.onClickUndoDelete() }
            show()
        }
    }

    override fun hideUndoDeleteSnack() {
        undoSnackbar?.dismiss()
        undoSnackbar = null
    }

    override fun showDialogEmptyList() {
        //TODO должен диалог отображаться или типо того
        Toast.makeText(requireContext(), "Список не должен быть пустым!", Toast.LENGTH_LONG).show()
    }

    override fun onCityInserted(position: Int) {
        presenter.onCityInsertedAt(position)
    }

    override fun setSelectedCity(position: Int) {
        if (targetFragment is TargetFragment) {
            (targetFragment as TargetFragment).onSelectCity(position)
        }
    }

    override fun navigateToPreviousScreen() {
        parentListener?.toBack()
    }

    override fun navigateToSearchCityScreen() {
        parentListener?.navigateTo(Screen.Factory.searchCity(this))
    }


}
