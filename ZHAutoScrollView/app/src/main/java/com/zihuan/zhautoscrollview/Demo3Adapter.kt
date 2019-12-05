package com.zihuan.zhautoscrollview

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.zihuan.baseadapter.RecyclerViewHolder
import com.zihuan.baseadapter.SpecialHolderAdapter
import com.zihuan.view.crvlibrary.ZRecyclerData
import com.zihuan.zhautoscrollview.slideswaphelper.SlideSwapAction

class Demo3Adapter(`object`: Any?) : SpecialHolderAdapter<Demo3Adapter.LeftScrollHolder>(`object`), ZRecyclerData {


    override fun convert(holder: LeftScrollHolder, position: Int, context: Context) {
        var entity = getEntity<String>(position)
        var item_text = holder.getTView<TextView>(R.id.item_text)
        var tv_del = holder.getTView<TextView>(R.id.tv_del)
        item_text.text = entity
        tv_del.setOnClickListener {
            baseDatas.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.rv_layout
    }

    override fun createHolder(view: View): LeftScrollHolder {
        return LeftScrollHolder(view)
    }

    class LeftScrollHolder(view: View) : RecyclerViewHolder(view), SlideSwapAction {
        override fun getActionWidth(): Float {
            return dp2px(70f)
        }

        override fun ItemView(): View {
            return view.findViewById(R.id.item_text)
        }

        private fun dp2px(dpValue: Float): Float {
            val scale = view.context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, scale)
        }
    }


}