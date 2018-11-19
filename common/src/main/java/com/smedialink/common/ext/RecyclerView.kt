package com.smedialink.common.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.horizontal(
        reverseLayout: Boolean = false,
        withAdapter: RecyclerView.Adapter<*>
) {
    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, reverseLayout)
    adapter = withAdapter
}

fun RecyclerView.vertical(
        reverseLayout: Boolean = false,
        withAdapter: RecyclerView.Adapter<*>
) {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, reverseLayout)
    adapter = withAdapter
}