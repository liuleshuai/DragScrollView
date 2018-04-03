package com.liukuo.dragscrollview;

import android.support.v4.widget.ViewDragHelper;
import android.view.View;

/**
 * Created by LiuKuo at 2018/4/3
 */
public class DragHelperCallBack extends ViewDragHelper.Callback {
    private final OuterLayout parent;

    public DragHelperCallBack(OuterLayout parent) {
        this.parent = parent;
    }

    @Override
    public boolean tryCaptureView(View child, int pointerId) {
        return true;
    }

    @Override
    public int clampViewPositionVertical(View child, int top, int dy) {
        return Math.max(0, top);
    }

    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        return 0;
    }

    @Override
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        super.onViewPositionChanged(changedView, left, top, dx, dy);
        parent.changeSize(top);
    }

    @Override
    public void onViewReleased(View releasedChild, float xvel, float yvel) {
//        if (releasedChild == parent) {
            parent.closeDown(releasedChild.getTop());
//        }
        super.onViewReleased(releasedChild, xvel, yvel);
    }
}