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
import com.jc.dargedit.pojo.Applet

class AppletViewDelegate(
    private var itemClick: OnItemClickListener<Applet>? = null,
    private var itemLongClick: OnItemLongClick<Applet>? = null
) :
    ItemViewDelegate<Applet, AppletViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.applet, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Applet) {
        holder.name.text = item.name
        val resources = holder.itemView.resources
        holder.img.background = resources.getDrawable(R.mipmap.ic_launcher)

        holder.itemView.setOnClickListener {
            itemClick?.onItemClick(it, holder.adapterPosition, item)
        }

        holder.itemView.setOnLongClickListener {
            itemLongClick?.onItemLongClick(it, holder, item)
            true
        }
        holder.cancel.background = if (!item.hide) {
            resources.getDrawable(R.drawable.ic_add)
        } else {
            resources.getDrawable(R.drawable.ic_remove)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val img: ImageView = itemView.findViewById(R.id.icon)
        val cancel: ImageView = itemView.findViewById(R.id.cancel)

    }
}