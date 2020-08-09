package com.jc.dargedit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.jc.dargedit.R
import com.jc.dargedit.pojo.EmptyView

class EmptyViewDelegate : ItemViewDelegate<EmptyView, EmptyViewDelegate.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onBindViewHolder(holder: ViewHolder, item: EmptyView) {
        val param = holder.itemView.layoutParams
        if (item.visiable) {
            param.height = ViewGroup.LayoutParams.WRAP_CONTENT
            param.width = ViewGroup.LayoutParams.MATCH_PARENT
            holder.itemView.visibility = View.VISIBLE
        } else {
            param.height = 0
            param.width = 0
            holder.itemView.visibility = View.GONE
        }

    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.empty_header, parent, false)
        )
    }
}