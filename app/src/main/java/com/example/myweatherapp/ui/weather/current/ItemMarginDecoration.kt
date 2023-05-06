package com.example.myweatherapp.ui.weather.current

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemMarginDecoration(private val margin: Int): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if(position != 0) {
            outRect.top = margin
            outRect.bottom = margin
            outRect.left = margin
            outRect.right = margin
        }
    }
}