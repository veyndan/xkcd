package com.veyndan.xkcd.ui

import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.veyndan.xkcd.*
import com.veyndan.xkcd.Comic
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

class FeedUI(state: State) : AnkoComponent<MainActivity> {

    var state = state
        set(value) {
            field = value
            (recyclerView.adapter as ItemAdapter).state = state
        }

    private lateinit var recyclerView: RecyclerView

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        coordinatorLayout {
            fitsSystemWindows = true
            lparams(width = matchParent, height = matchParent)
            themedAppBarLayout(R.style.ThemeOverlay_AppCompat_ActionBar) {
                toolbar {
                    titleResource = R.string.app_name
                    menu.apply {
                        menuItem(title = "Filter") {
                            iconRes = R.drawable.ic_filter_list_black_24dp
                            showAsAction = MenuItem.SHOW_AS_ACTION_ALWAYS
                        }
                        menuItem(title = "Sort") {
                            iconRes = R.drawable.ic_sort_black_24dp
                            showAsAction = MenuItem.SHOW_AS_ACTION_ALWAYS
                        }
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                }
            }.lparams(width = matchParent, height = wrapContent)
            recyclerView = recyclerView {
                layoutManager = LinearLayoutManager(ctx)
                adapter = ItemAdapter(state)
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }

    data class State(val comics: List<Comic>)
}
