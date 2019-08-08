package com.zihuan.zhautoscrollview

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.zihuan.autoscrollview.ZHAutoScrollView
import com.zihuan.baseadapter.R
import com.zihuan.baseadapter.RecyclerAdapter
import com.zihuan.baseadapter.RecyclerViewHolder
import com.zihuan.zhautoscrollview.activity.LeftSlidDeleteActivity2

class Demo2Adapter(`object`: Any?) : RecyclerAdapter(`object`) {
    override fun convert(holder: RecyclerViewHolder, position: Int, context: Context) {
        var entity = getEntity<String>(position)
        var scroll_item = holder.getTView<ZHAutoScrollView>(R.id.scroll_item)
        var item_text = holder.getTView<TextView>(R.id.item_text)
        var rightLayout = holder.getView(R.id.right_Layout)
        item_text.text = entity
        if (position % 2 == 0) {
            scroll_item.setIsCanScroll(false)
        } else {
            scroll_item.setIsCanScroll(true)
        }
        item_text.setOnClickListener {
            Toast.makeText(context, "点击$position", Toast.LENGTH_SHORT).show()
        }
        item_text.setOnLongClickListener {
//            添加是否可拖动的逻辑
            Toast.makeText(context, "长按$position", Toast.LENGTH_SHORT).show()
            (context as LeftSlidDeleteActivity2).itemTouchHelper.startDrag(holder)
            return@setOnLongClickListener true
        }
        rightLayout.setOnClickListener {
            Log.e("点击", "删除 $entity")
            baseDatas.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, baseDatas.size - 1)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.rv_left_del_layout
    }
}