package com.liuleshuai.draglibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by LiuKuo at 2018/1/16
 */

public class InnerScrollView extends ScrollView {

    private boolean stopScroll = false;
    private boolean scrollEnable = true;

    public InnerScrollView(Context context) {
        this(context, null);
    }

    public InnerScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (stopScroll) {
            // 返回true或false都可以停止ScrollView滚动
            return true;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t == 0) {
            scrollEnable = true;
        } else {
            scrollEnable = false;
        }
    }

    public void setStopScroll(boolean stopScroll) {
        this.stopScroll = stopScroll;
    }

    public boolean isScrollEnable() {
        return scrollEnable;
    }
}
