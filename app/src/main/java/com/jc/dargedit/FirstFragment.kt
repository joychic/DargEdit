package com.jc.dargedit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.jc.dargedit.adapter.*
import com.jc.dargedit.pojo.Applet
import com.jc.dargedit.pojo.EmptyView
import com.jc.dargedit.pojo.Header
import com.jc.dargedit.pojo.Title
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val adapter = MultiTypeAdapter()
    private val finalList = getData()
    internal var appletDragList: MutableMap<Applet, Any> = ArrayMap()
    private val emptyView = EmptyView(true)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)

        val offsetSize = 1
        adapter.register(AppletViewDelegate(object : OnItemClickListener<Applet> {
            override fun onItemClick(itemView: View, position: Int, value: Applet) {
                val contains = appletDragList.contains(value)
                value.hide = !contains
                adapter.notifyItemChanged(position)
                if (contains) {
                    //  回到原来的位置
                    val toPosition = findPastPosition(value)
                    swapItem(position, toPosition)
                    appletDragList.remove(value)
                } else {
                    val toPosition = appletDragList.size + offsetSize
                    // appletDragList 记录上一个元素的值
                    val preValue = findPreInFinal(value) ?: return
                    appletDragList[value] = preValue
                    swapItem(position, toPosition)
                }

                if (emptyView.visiable != appletDragList.isEmpty()) {
                    emptyView.visiable = appletDragList.isEmpty()
                    adapter.notifyItemChanged(appletDragList.size + 1)
                }

            }

            private fun findPreInFinal(applet: Applet): Any? {
                finalList.forEachIndexed { index, value ->
                    if (value == applet) {
                        return finalList[index - 1]
                    }
                }
                return null
            }

            private fun findPastPosition(applet: Applet): Int {

                var pre = appletDragList[applet]
                while (appletDragList.contains(pre)) {
                    pre = appletDragList[pre]
                }
                adapter.items.forEachIndexed { index, value ->
                    if (value == pre) {
                        return index
                    }
                }
                return -1
            }


        }, object : OnItemLongClick<Applet> {
            override fun onItemLongClick(
                itemView: View,
                vh: RecyclerView.ViewHolder,
                value: Applet
            ) {
                if (appletDragList.contains(value)) {
                    mItemTouchHelper.startDrag(vh)
                }
            }

        }))
        adapter.register(TitleViewDelegate())
        adapter.register(HeaderViewDelegate())
        adapter.register(EmptyViewDelegate())
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(context, SPAN_COUNT)
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val item = adapter.items[position]
                return if (item is Applet) 1 else SPAN_COUNT
            }
        }
        layoutManager.spanSizeLookup = spanSizeLookup
        recyclerView.layoutManager = layoutManager

        mItemTouchHelper.attachToRecyclerView(recyclerView)

        adapter.items = getData()
        adapter.notifyDataSetChanged()
    }


    private fun getData(): ArrayList<Any> {
        val items = ArrayList<Any>()
        // todo  模拟数据
        items.add(Header("你也可以将常用的应用添加到首页，\n也可以按住拖动调整应用位置"))
        items.add(emptyView)
        items.add(Header("全部应用"))

        items.add(Title("财富管理"))
        items.add(Applet("余额宝", ""))
        items.add(Applet("花呗", ""))
        items.add(Applet("芝麻信用", ""))
        items.add(Applet("借呗", ""))
        items.add(Applet("股票", ""))
        items.add(Applet("基金", ""))
        items.add(Applet("汇率换算", ""))
        items.add(Applet("蚂蚁保险", ""))


        items.add(Title("资金往来"))
        items.add(Applet("红包", ""))
        items.add(Applet("情亲卡", ""))
        items.add(Applet("商家服务", ""))


        items.add(Title("购物娱乐"))
        items.add(Applet("彩票", ""))
        items.add(Applet("奖励金", ""))
        items.add(Applet("游戏中心", ""))
        items.add(Applet("全球惠", ""))
        items.add(Applet("口碑", ""))
        items.add(Applet("出境", ""))


        items.add(Title("教育公益"))
        items.add(Applet("校园", ""))
        items.add(Applet("爱心捐款", ""))
        items.add(Applet("蚂蚁森林", ""))
        items.add(Applet("蚂蚁庄园", ""))
        items.add(Applet("运动", ""))
        items.add(Applet("小宝", ""))
        items.add(Applet("3小时公益", ""))


        items.add(Title("第三方服务"))
        items.add(Applet("电影演出", ""))
        items.add(Applet("滴滴出行", ""))
        items.add(Applet("饿了么", ""))
        items.add(Applet("天猫", ""))
        items.add(Applet("淘宝", ""))
        items.add(Applet("火车票机票", ""))
        items.add(Applet("酒店出游", ""))
        items.add(Applet("高德打车", ""))
        items.add(Applet("哈啰出行", ""))
        items.add(Applet("我的小程序", ""))
        items.add(Applet("盒马", ""))
        items.add(Applet("优酷视频", ""))
        items.add(Applet("景点门票", ""))
        return items
    }

    // todo 拖拽
    val mItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            if (recyclerView.layoutManager is GridLayoutManager) {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, 0)
            }
            if (recyclerView.layoutManager is LinearLayoutManager) {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            return makeMovementFlags(0, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            // 不支持拖拽的区域 return
            if (toPosition > appletDragList.size - 1) {
                return false
            }

            swapItem(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }

        /**
         * 长按选中 Item 高亮
         */
        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder?.itemView?.setBackgroundColor(Color.LTGRAY)
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        /**
         * 手指松开的时候还原
         */
        override fun clearView(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.setBackgroundColor(0);
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }
    })

    private fun swapItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(adapter.items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(adapter.items, i, i - 1)
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    companion object {
        private const val SPAN_COUNT = 5
    }
}