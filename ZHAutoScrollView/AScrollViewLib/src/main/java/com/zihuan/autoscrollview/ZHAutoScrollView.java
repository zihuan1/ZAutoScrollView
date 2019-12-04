package com.zihuan.autoscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;


/****
 */
public class ZHAutoScrollView extends HorizontalScrollView {


    public ZHAutoScrollView(Context context) {
        super(context);
        init(context, null);
    }

    public ZHAutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZHAutoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public static final String TAG = ZHAutoScrollView.class.getSimpleName();
    private boolean isLeft = false;//默认左边
    private int rightLayoutWidth;
    private int leftLayoutWidth;
    private int contentLayoutWidth;
    private int margin;//左右边距 当前view距离屏幕左右两边的和
    private int range;//滚动阈值 超过此值后显示
    boolean isIntercept = false;//是否拦截事件
    private boolean isCanScroll = true;//是否能滚动

    public void setRightLayoutWidth(int rightLayoutWidth) {
        this.rightLayoutWidth = rightLayoutWidth;
    }

    public void setContentLayoutWidth(int contentLayoutWidth) {
        this.contentLayoutWidth = contentLayoutWidth;
    }

    public void setRange(int range) {
        this.range = range;
    }


    /***
     * 是否可以滑动
     * @param canScroll
     */
    public void setIsCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }

    public boolean getIsCanScroll() {
        return isCanScroll;
    }

    private void init(Context context, AttributeSet attrs) {
        setHorizontalScrollBarEnabled(false);
        contentLayoutWidth = getScreenSize(getContext()).widthPixels;//  屏幕宽度
        rightLayoutWidth = dp2px(getContext(), 200);// 右边布局的宽度
        range = dp2px(getContext(), 70);// 移动多少开始切换阈值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollView);
        rightLayoutWidth = dp2px(context, typedArray.getInteger(R.styleable.AutoScrollView_sv_right_width, 0));
        int ran = typedArray.getInteger(R.styleable.AutoScrollView_sv_move_range, 0);
        range = ran == 0 ? rightLayoutWidth / 2 : dp2px(context, ran);
        margin = dp2px(context, typedArray.getInteger(R.styleable.AutoScrollView_sv_margin, 0));
        contentLayoutWidth -= margin;
        typedArray.recycle();
        post(new Runnable() {
            @Override
            public void run() {
                apply();
            }
        });
    }

    private void apply() {
//        isLeft = true;
        changeLayout();
//        scrollTo(0, 0);
    }

    View rightLayout;

    private void changeLayout() {
        try {
            ViewGroup mainLayout = (ViewGroup) getChildAt(0);
            View contentLayout = mainLayout.getChildAt(0);
            rightLayout = mainLayout.getChildAt(1);
//            ViewGroup contentLayout = (ViewGroup) mainLayout.getChildAt(1);
//            rightLayout = (ViewGroup) mainLayout.getChildAt(2);
            if (contentLayout.getMeasuredWidth() == contentLayoutWidth && rightLayout.getMeasuredWidth() == rightLayoutWidth) {
                Logger("状态未改变");
                return;
            }
            ViewGroup.LayoutParams layoutParams = contentLayout.getLayoutParams();
            layoutParams.width = contentLayoutWidth;
            contentLayout.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams layoutParams2 = rightLayout.getLayoutParams();
            layoutParams2.width = rightLayoutWidth;
            rightLayout.setLayoutParams(layoutParams2);
            if (rightLayout.getVisibility() == View.GONE) {
                rightLayout.setVisibility(VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int scrollx = getScrollX();
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN && scrollx >= rightLayoutWidth) {//展开的状态下
            if (contentLayoutWidth - ev.getX() > rightLayoutWidth) {//点击的是左边的view
                Logger("准备拦截");
                isIntercept = true;
                return true;
            }
            Logger("放行");
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isCanScroll) return false;
        int scrollx = getScrollX();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                Logger("移动");
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Logger("up");
                if (isIntercept) {
                    isIntercept = false;
                    smoothScrollTo(0, 0);
                    Logger("拦截点击");
                    return true;
                }
                if (scrollx > 0)
                    if (scrollx > range) {
                        ZHAutoScrollViewHelper.getInstance().setLastView(this);
                        smoothScrollTo(rightLayoutWidth, 0);
                    } else {
                        smoothScrollTo(0, 0);
                    }
                return scrollx > 0 ? true : false;
        }
        return super.onTouchEvent(ev);
    }

    public static int dp2px(Context context, float dpValue) {
        DisplayMetrics scale = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, scale);
    }

    /***
     * 滑动复位
     */
    public void reset() {
        if (getScrollX() <= 0) return;
        post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, 0);
                Logger("复位");
            }
        });
    }

    public boolean isDebug = true;

    private void Logger(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }
}

