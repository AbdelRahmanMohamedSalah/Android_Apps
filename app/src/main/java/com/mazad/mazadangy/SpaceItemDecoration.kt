package com.mazad.mazadangy

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(mspace: Int) : RecyclerView.ItemDecoration() {

    val space = mspace

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.apply {

            when ((parent!!.getChildLayoutPosition(view)) % 3) {
                0, 1 -> {
                    left = space
                    right = space
                    top = space
                }

                2 -> {
                    top = space
                    right = space
                }

            }

        }

    }
}
