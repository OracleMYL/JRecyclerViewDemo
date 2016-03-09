package com.jtech.jrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * item长点击事件回调
 * Created by wuxubaiyang on 2016/3/7.
 */
public interface OnItemLongClickListener {
    boolean onItemLongClick(RecyclerHolder holder, View view, int position);
}