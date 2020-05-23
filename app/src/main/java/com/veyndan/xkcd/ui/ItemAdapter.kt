package com.veyndan.xkcd.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class ItemAdapter(state: FeedUI.State) : RecyclerView.Adapter<ViewHolder<ItemUi>>() {

    var state = state
        set(value) {
            val previousCount = itemCount
            field = value
            notifyItemRangeInserted(previousCount, itemCount - previousCount)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemUi> {
        return ViewHolder(parent, ItemUi())
    }

    override fun getItemCount(): Int = state.comics.size

    override fun onBindViewHolder(holder: ViewHolder<ItemUi>, position: Int) {
        val comic = state.comics[position]
        holder.component.title.text = comic.title
    }
}
