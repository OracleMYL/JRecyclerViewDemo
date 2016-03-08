package com.jtech_jrecyclerviewdemo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.jrecyclerview.JRecyclerView;
import com.jtech_jrecyclerviewdemo.R;
import com.jtech_jrecyclerviewdemo.bean.UserEntity;

/**
 * 用户列表适配器
 * Created by wuxubaiyang on 2016/2/6.
 */
public class UserListAdapter extends JRecyclerView.RecyclerAdapter<UserEntity> {
    public UserListAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.view_user_item, parent, false);
    }

    @Override
    public void convert(JRecyclerView.RecyclerHolder holder, UserEntity item, int position) {
        holder.setText(R.id.textview_user_item_name, "姓名：" + item.getName());
        holder.setText(R.id.textview_user_item_sex, "性别：" + item.getSex());
        holder.setText(R.id.textview_user_item_age, "年龄：" + item.getAge());
    }
}