package com.muazekici.n11sample.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(
    private val startSpace: Int,
    private val topSpace: Int,
    private val endSpace: Int,
    private val bottomSpace: Int = 0,
    private val includeFirstItem: Boolean = false
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) return
        with(outRect) {
            top = if (itemPosition == 0 && !includeFirstItem) 0 else topSpace
            left = startSpace
            bottom = bottomSpace
            right = endSpace
        }
    }
}