package com.jc.dargedit.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener<in T> {
    fun onItemClick(itemView: View, position: Int, value: T)

}

interface OnItemLongClick<in T> {
    fun onItemLongClick(itemView: View, vh: RecyclerView.ViewHolder, value: T)
}