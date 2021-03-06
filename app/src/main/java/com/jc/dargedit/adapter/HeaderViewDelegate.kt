  package com.jc.dargedit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.jc.dargedit.R
import com.jc.dargedit.pojo.Header
import com.jc.dargedit.pojo.Title

class HeaderViewDelegate : ItemViewDelegate<Header, HeaderViewDelegate.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Header) {
        holder.title.text = item.title
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.header_title, parent,false))
    }
}