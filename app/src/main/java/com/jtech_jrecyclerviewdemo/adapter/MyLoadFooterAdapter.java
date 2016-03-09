package com.jtech_jrecyclerviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.jrecyclerview.LoadFooterAdapter;
import com.jtech.jrecyclerview.RecyclerHolder;
import com.jtech_jrecyclerviewdemo.R;

/**
 * 自定义的足部适配器
 * Created by wuxubaiyang on 16/3/9.
 */
public class MyLoadFooterAdapter extends LoadFooterAdapter {

    @Override
    public View getFooterView(LayoutInflater layoutInflater, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.view_load_footer, parent, false);
    }

    @Override
    public void loadFailState(RecyclerHolder recyclerHolder) {
        recyclerHolder.setText(R.id.textview_load_footer, "加载失败,请重试");
    }

    @Override
    public void loadingState(RecyclerHolder recyclerHolder) {
        recyclerHolder.setText(R.id.textview_load_footer, "加载中");
    }

    @Override
    public void noMoreDataState(RecyclerHolder recyclerHolder) {
        recyclerHolder.setText(R.id.textview_load_footer, "无更多数据");
    }

    @Override
    public void normalState(RecyclerHolder recyclerHolder) {
        recyclerHolder.setText(R.id.textview_load_footer, "滑动加载更多");
    }
}
