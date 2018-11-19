package com.smedialink.common.recyclerview

import androidx.recyclerview.widget.DiffUtil

class ItemsDiffCallback private constructor() : DiffUtil.ItemCallback<ItemViewType>() {

    override fun areItemsTheSame(itemViewType: ItemViewType, t1: ItemViewType): Boolean {
        return itemViewType.viewType == t1.viewType
    }

    override fun areContentsTheSame(itemViewType: ItemViewType, t1: ItemViewType): Boolean {
        return itemViewType == t1
    }

    companion object {
        val ITEMS_DIFF_CALLBACK = ItemsDiffCallback()
    }

}
