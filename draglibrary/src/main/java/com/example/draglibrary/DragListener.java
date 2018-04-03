package com.example.draglibrary;

/**
 * Created by LiuKuo at 2018/4/3
 */

public interface DragListener {

    /**
     * 关闭窗口回调
     */
    void close();

    /**
     * 下拉窗口变化回调
     *
     * @param top 下拉距离
     */
    void changeSize(int top);

}
