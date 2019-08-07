package com.zihuan.zhautoscrollview.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zihuan.zhautoscrollview.Demo2Adapter
import com.zihuan.baseadapter.ViewOnItemClick
import com.zihuan.autoscrollview.ZHAutoScrollViewHelper
import kotlinx.android.synthetic.main.activity_left_slid_layout.*


class LeftSlidDeleteActivity2 : Activity(), ViewOnItemClick {
    override fun setOnItemClickListener(view: View?, postion: Int) {
        Toast.makeText(this, "点击$postion", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.zihuan.baseadapter.R.layout.activity_left_slid_layout)
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
    }
}