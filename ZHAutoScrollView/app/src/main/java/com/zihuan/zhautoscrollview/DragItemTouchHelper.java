package com.zihuan.zhautoscrollview;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragItemTouchHelper extends ItemTouchHelper.Callback {

    RecyclerView.Adapter mAdapter;
    List mList;

    public DragItemTouchHelper(RecyclerView.Adapter adapter, List list) {
        mAdapter = adapter;
        mList = list;
    }

//    是设置是否滑动时间，以及拖拽的方向，如果是列表布局的话则拖拽方向有DOWN和UP，如果是网格布局的话有DOWN和UP和LEFT和RIGHT4个方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.e(" ", "getMovementFlags()");
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

//    正在拖动中
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.e(" ", "onMove()");
        //得到当拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }
//拖拽完成
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                Toast.makeText(MainActivity.this, "拖拽完成 方向" + direction, Toast.LENGTH_SHORT).show();
        Log.e(" ", "拖拽完成 方向" + direction);

    }

//    选中
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.e(" ", "onSelectedChanged()");
//        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE)
//            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
    }

//    拖拽完成
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.e(" ", "clearView()");
//        viewHolder.itemView.setBackgroundColor(0);

    }

    //重写拖拽不可用
    @Override
    public boolean isLongPressDragEnabled() {
        Log.e(" ", "isLongPressDragEnabled()");
        return false;
    }
//    };
}
