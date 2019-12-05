package com.zihuan.zhautoscrollview.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import com.zihuan.autoscrollview.ZHAutoScrollViewHelper
import com.zihuan.zhautoscrollview.Demo2Adapter
import com.zihuan.zhautoscrollview.DragItemTouchHelper
import com.zihuan.zhautoscrollview.R
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
                .setData(mDemoData)
        var zhLeftScrollViewHelper = ZHAutoScrollViewHelper.getInstance()
        zhLeftScrollViewHelper.bindRecyclerView(rv_left_slid.getRecyclerView())
        itemTouchHelper = ItemTouchHelper(DragItemTouchHelper(demoAdapter, demoAdapter.baseDatas))
        itemTouchHelper.attachToRecyclerView(rv_left_slid.getRecyclerView())
    }

    lateinit var itemTouchHelper: ItemTouchHelper
}