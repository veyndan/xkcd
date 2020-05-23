package com.veyndan.xkcd.ui

import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ItemUi : AnkoComponent<ViewGroup> {

    lateinit var title: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        cardView {
            useCompatPadding = true
            lparams(width = matchParent, height = wrapContent)
            title = textView("Hello")
        }
    }
}
