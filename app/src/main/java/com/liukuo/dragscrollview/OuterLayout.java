package com.liukuo.dragscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by LiuKuo at 2018/1/12
 */

public class OuterLayout extends LinearLayout {
    private OuterLayout outerLayout;
    private ViewDragHelper mDragHelper;
    private View dragView;
    private DragListener listener;
    private float downY, moveY;
    private Context context;
    /**
     * 是否滑动中
     */
    private boolean swapStatus = false;
    /**
     * Parent是否是滑动状态
     */
    private boolean swapParent = false;
    /**
     * Child是否是滑动状态
     */
    private boolean swapChild = false;
    /**
     * 关闭窗口的下拉比例
     */
    private float radio;

    public OuterLayout(Context context) {
        this(context, null);
    }

    public OuterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OuterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        outerLayout = this;
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.dray_scrollview);
        radio = a.getFloat(R.styleable.dray_scrollview_dray_radio, 0.5f);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new RuntimeException("内部必须只能有一个View!");
        }
        if (mDragHelper == null) {
            dragView = getChildAt(0);
            mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallBack(this));
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        Log.d("LK", action + "  " + ((InnerScrollView) dragView).isScrollEnable());
        // 将事件传给ViewDragHelper
        // STATE_DRAGGING返回true，否则返回false
        // 内控件的top为0时，scrollEnable为true
        return mDragHelper.shouldInterceptTouchEvent(ev) || ((InnerScrollView) dragView).isScrollEnable();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // scrollEnable肯定为true时才进入
                downY = event.getY();
                dragView.dispatchTouchEvent(event);
                mDragHelper.processTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = event.getY();
                if (!swapStatus) {
                    // 每次滑动只走一次
                    swapStatus = true;
                    Log.d("LK", dragView.getTop() + "");
                    if ((dragView.getTop() == 0) && (moveY - downY > 0)) {
                        swapParent = true;
                        // 防止拖动向上时ScrollView滑动，锁死内控件不让其滑动
                        ((InnerScrollView) dragView).setStopScroll(true);
                    } else {
                        // 直接向上滑动的话，只走一次。
                        swapChild = true;
                    }
                }
                if (swapParent) {
                    mDragHelper.processTouchEvent(event);
                }
                if (swapChild) {
                    dragView.dispatchTouchEvent(event);
                    mDragHelper.cancel();
                }
                break;
            default:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((InnerScrollView) dragView).setStopScroll(false);
                    swapStatus = false;
                    swapParent = false;
                    swapChild = false;
                }
                dragView.dispatchTouchEvent(event);
                mDragHelper.processTouchEvent(event);
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 判断下拉是否关闭
     */
    public void closeDown(int top) {
        if (((float) top) / getHeight() > radio) {
            mDragHelper.smoothSlideViewTo(dragView, 0, getRootView().getHeight());
            if (listener != null) {
                listener.close();
            }
        } else {
            mDragHelper.smoothSlideViewTo(dragView, 0, 0);
        }
        invalidate();
        swapStatus = false;
    }

    /**
     * 下拉尺寸动画效果
     *
     * @param top 距顶部距离
     */
    public void changeSize(int top) {
        if (listener != null) {
            listener.changeSize(top);
        }
    }

    public void setDragListener(DragListener listener) {
        this.listener = listener;
    }
}