package com.zihuan.zhautoscrollview.activity

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.widget.Toast
import com.zihuan.zhautoscrollview.R
import kotlinx.android.synthetic.main.activity_draw.*


class DrawActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)
        tv_left.setOnClickListener {
            Toast.makeText(this, "左边", Toast.LENGTH_SHORT).show()
        }
        tv_right.setOnClickListener {
            Toast.makeText(this, "右边", Toast.LENGTH_SHORT).show()
        }
        var index = 0

//        dl_test.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//        val toggle = ActionBarDrawerToggle(
//                this, dl_test, null, R.string.left, R.string.right)
//        dl_test.setDrawerListener(toggle)
        tv_title.setOnClickListener {
//            toggle.syncState()
            if (index % 2 == 0) {
                dl_test.closeDrawer(Gravity.RIGHT, true)
                dl_test.openDrawer(Gravity.LEFT)
            } else {
                dl_test.closeDrawer(Gravity.LEFT, true)
                dl_test.openDrawer(Gravity.RIGHT)
            }
            index++
        }
    }

}