package com.jtech.jrecyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * JRecyclerView适配器
 *
 * @param <T>
 * @author wuxubaiyang
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {
    /**
     * Activity对象
     */
    private Activity activity;
    /**
     * 数据集合
     */
    private List<T> realDatas = new ArrayList();

    /**
     * 主构造
     *
     * @param activity Activity对象
     */
    public RecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    /**
     * 得到Activity对象
     *
     * @return
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * 获取item对象
     *
     * @param position 位置
     */
    public T getItem(int position) {
        if (null != realDatas && realDatas.size() > position) {
            return realDatas.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (null != realDatas) {
            return realDatas.size();
        }
        return 0;
    }

    /**
     * 获取真实数据
     *
     * @return
     */
    public List<T> getRealDatas() {
        return realDatas;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View root = createView(inflater, parent, viewType);
        return new RecyclerHolder(root);
    }

    /**
     * 创建item的根视图
     *
     * @param inflater LayoutInflater
     * @param parent   父视图
     * @param viewType 视图类型
     * @return item根视图
     */
    public abstract View createView(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        if (null == realDatas || position >= realDatas.size()) {
            convert(holder, null, position);
        } else {
            convert(holder, realDatas.get(position), position);
        }
    }

    /**
     * Recycler适配器填充方法
     *
     * @param holder viewholder
     * @param item   javabean
     */
    public abstract void convert(RecyclerHolder holder, T item, int position);

    /**
     * 设置数据
     *
     * @param datas      数据
     * @param isLoadMore 是否为加载更多
     * @return 适配器对象
     */
    public void setDatas(Collection<T> datas, boolean isLoadMore) {
        if (isLoadMore) {
            if (null != datas && null != realDatas) {
                Iterator<T> iterator = datas.iterator();
                while (iterator.hasNext()) {
                    T t = iterator.next();
                    realDatas.add(t);
                }
            }
        } else {
            realDatas = new ArrayList<T>(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加单条数据
     *
     * @param data 数据
     * @return 适配器对象
     */
    public void addData(T data) {
        if (null != data && null != realDatas) {
            addData(realDatas.size(), data);
        }
    }

    /**
     * 添加数据到指定位置
     *
     * @param index 指定位置
     * @param data  数据
     * @return 适配器对象
     */
    public void addData(int index, T data) {
        if (null != data && null != realDatas) {
            realDatas.add(index, data);
        }
        notifyItemInserted(index);
    }

    /**
     * 移除数据
     *
     * @param position 数据位置
     * @return 适配器对象
     */
    public void removeData(int position) {
        if (null != realDatas && realDatas.size() > position) {
            realDatas.remove(position);
        }
        notifyItemRemoved(position);
    }
}