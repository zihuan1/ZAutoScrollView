package com.zihuan.autoscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.zihuan.zhautoscrollview.BuildConfig;
import com.zihuan.baseadapter.R;

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
    private boolean isLeft = true;//默认左边
    private int rightLayoutWidth;
    private int leftLayoutWidth;
    private int margin;//左右边距 当前view距离屏幕左右两边的和
    private int range;//滚动阈值 超过此值后显示
    boolean isIntercept = false;//是否拦截事件

    public void setRightLayoutWidth(int rightLayoutWidth) {
        this.rightLayoutWidth = rightLayoutWidth;
    }

    public void setLeftLayoutWidth(int leftLayoutWidth) {
        this.leftLayoutWidth = leftLayoutWidth;
    }

    public void setRange(int range) {
        this.range = range;
    }

    private boolean isCanScroll = true;

    /***
     * 是否可以滑动
     * @param canScroll
     */
    public void setIsCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }

    int screenWidth;

    private void init(Context context, AttributeSet attrs) {
        setHorizontalScrollBarEnabled(false);
        leftLayoutWidth = getScreenSize(getContext()).widthPixels;//  屏幕宽度
        rightLayoutWidth = dp2px(getContext(), 200);// 右边布局的宽度
        range = dp2px(getContext(), 70);// 移动多少开始切换阈值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollView);
        rightLayoutWidth = dp2px(context, typedArray.getInteger(R.styleable.AutoScrollView_sv_right_width, 0));
        int ran = typedArray.getInteger(R.styleable.AutoScrollView_sv_move_range, 0);
        range = ran == 0 ? rightLayoutWidth / 2 : dp2px(context, ran);
        margin = dp2px(context, typedArray.getInteger(R.styleable.AutoScrollView_sv_margin, 0));
        leftLayoutWidth -= margin;
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
        scrollTo(0, 0);
    }


    private void changeLayout() {
        try {
            ViewGroup mainLayout = (ViewGroup) getChildAt(0);
            ViewGroup left = (ViewGroup) mainLayout.getChildAt(0);
            ViewGroup right = (ViewGroup) mainLayout.getChildAt(1);
            if (left.getMeasuredWidth() == leftLayoutWidth && right.getMeasuredWidth() == rightLayoutWidth) {
                Logger("状态未改变");
                return;
            }
            ViewGroup.LayoutParams layoutParams = left.getLayoutParams();
            layoutParams.width = leftLayoutWidth;
            left.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams layoutParams2 = right.getLayoutParams();
            layoutParams2.width = rightLayoutWidth;
            right.setLayoutParams(layoutParams2);
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
            if (leftLayoutWidth - ev.getX() > rightLayoutWidth) {//点击的是左边的view
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
        if (!isCanScroll) return true;
        int scrollx = getScrollX();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
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
                if (scrollx > range) {
//                    isLeft = false;
                    ZHAutoScrollViewHelper.getInstance().setLastView(this);
                    smoothScrollTo(rightLayoutWidth, 0);
                } else {
                    smoothScrollTo(0, 0);
                }


//                if (isLeft) {
//                    if (scrollx > range) {
//                        isLeft = false;
//                        ZHAutoScrollViewHelper.getInstance().setLastView(this);
//                        smoothScrollTo(rightLayoutWidth, 0);
//                    } else {
//                        smoothScrollTo(0, 0);
//                    }
//                } else {
//                    if (scrollx < (rightLayoutWidth - range)) {
//                        isLeft = true;
//                        smoothScrollTo(0, 0);
//                    } else {
//                        ZHAutoScrollViewHelper.getInstance().setLastView(this);
//                        smoothScrollTo(rightLayoutWidth, 0);
//                    }
//                }
                return true;
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

    private void Logger(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }
}

