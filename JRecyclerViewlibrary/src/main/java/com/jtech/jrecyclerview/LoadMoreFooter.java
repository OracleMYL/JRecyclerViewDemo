package com.jtech.jrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 加载更多的足部抽象类
 * Created by wuxubaiyang on 2016/3/7.
 */
public abstract class LoadMoreFooter {

    /**
     * 获取足部视图
     *
     * @return 足部视图
     */
    public abstract View getFooterView(LayoutInflater layoutInflater, ViewGroup parent);

    /**
     * 加载失败
     */
    public abstract void loadFailState(JRecyclerView.RecyclerHolder recyclerHolder);

    /**
     * 加载中
     */
    public abstract void loadingState(JRecyclerView.RecyclerHolder recyclerHolder);

    /**
     * 没有更多数据
     */
    public abstract void noMoreDataState(JRecyclerView.RecyclerHolder recyclerHolder);

    /**
     * 一般状态
     */
    public abstract void normalState(JRecyclerView.RecyclerHolder recyclerHolder);
}