package com.veyndan.xkcd.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

class ViewHolder<out C : AnkoComponent<ViewGroup>>(parent: ViewGroup, val component: C)
    : RecyclerView.ViewHolder(component.createView(AnkoContext.create(parent.context, parent)))
