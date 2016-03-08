package com.jtech.jrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * item点击事件回调
 * Created by wuxubaiyang on 2016/3/7.
 */
public interface OnItemClickListener {
    void onItemClick(RecyclerView.ViewHolder holder, View view, int position);
}