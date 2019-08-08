package com.zihuan.zhautoscrollview.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import com.zihuan.autoscrollview.ZHAutoScrollViewHelper
import com.zihuan.baseadapter.R
import com.zihuan.zhautoscrollview.Demo2Adapter
import com.zihuan.zhautoscrollview.DragItemTouchHelper
import kotlinx.android.synthetic.main.activity_left_slid_layout.*


class LeftSlidDeleteActivity2 : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_slid_layout)
        var demoAdapter = Demo2Adapter(this)
        var mDemoData = ArrayList<String>()
        for (i in 0..30) {
            mDemoData.add("昵称$i")
        }
        rv_left_slid.buildVerticalLayout(demoAdapter)
                .setPullEnabled(false)
                .setData(mDemoData)
        var zhLeftScrollViewHelper = ZHAutoScrollViewHelper.getInstance()
        zhLeftScrollViewHelper.bindRecyclerView(rv_left_slid.getRecyclerView())
        itemTouchHelper = ItemTouchHelper(DragItemTouchHelper(demoAdapter, demoAdapter.baseDatas))
        itemTouchHelper.attachToRecyclerView(rv_left_slid.getRecyclerView())
    }

    lateinit var itemTouchHelper: ItemTouchHelper
//    override fun setOnItemLongClickListener(view: View?, postion: Int) {
//        Toast.makeText(this, "长按$postion", Toast.LENGTH_SHORT).show()
//        itemTouchHelper.startDrag(rv_left_slid.getRecyclerView().getChildViewHolder(rv_left_slid.getRecyclerView().getChildAt(postion)))
//    }
//
//    override fun setOnItemClickListener(view: View?, postion: Int) {
//        Toast.makeText(this, "点击$postion", Toast.LENGTH_SHORT).show()
//    }
}