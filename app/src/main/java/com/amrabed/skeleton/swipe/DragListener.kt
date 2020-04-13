package com.amrabed.skeleton.swipe

import androidx.recyclerview.widget.RecyclerView

interface DragListener {
    fun onDrag(holder: RecyclerView.ViewHolder)
    fun onItemMoved(source: RecyclerView.ViewHolder, destination: RecyclerView.ViewHolder)
    fun onItemRemoved(holder: RecyclerView.ViewHolder)
    fun onItemHidden(holder: RecyclerView.ViewHolder)
}