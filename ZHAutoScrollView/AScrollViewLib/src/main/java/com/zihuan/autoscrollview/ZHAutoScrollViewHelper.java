package com.zihuan.autoscrollview;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;


public class ZHAutoScrollViewHelper {
    private static ZHAutoScrollViewHelper mZhAutoScrollViewHelper;
    private ZHAutoScrollView mZhAutoScrollView;

    public static ZHAutoScrollViewHelper getInstance() {
        return mZhAutoScrollViewHelper != null ? mZhAutoScrollViewHelper : (mZhAutoScrollViewHelper = new ZHAutoScrollViewHelper());
    }


    /***
     * 设置滑动的view
     * @param zhAutoScrollView
     */
    public void setLastView(ZHAutoScrollView zhAutoScrollView) {
        if (isHasLastView() && mZhAutoScrollView != zhAutoScrollView) {
            mZhAutoScrollView.reset();
        }
        mZhAutoScrollView = zhAutoScrollView;
    }

    public ZHAutoScrollView getLastView() {
        return mZhAutoScrollView;
    }

    //是否有最后一个view
    private boolean isHasLastView() {
        return getLastView() != null;
    }

    public void bindRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("状态", "newState " + newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && isHasLastView()) {
                    mZhAutoScrollView.reset();
                }

            }
        });

    }

}
