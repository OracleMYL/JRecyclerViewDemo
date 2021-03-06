package com.jtech_jrecyclerviewdemo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.jrecyclerview.JRecyclerView;
import com.jtech.jrecyclerview.RecyclerAdapter;
import com.jtech.jrecyclerview.RecyclerHolder;
import com.jtech_jrecyclerviewdemo.R;
import com.jtech_jrecyclerviewdemo.bean.UserEntity;

/**
 * 用户列表适配器
 * Created by wuxubaiyang on 2016/2/6.
 */
public class UserListAdapter extends RecyclerAdapter<UserEntity> {
    private OnItemTouchToMove onItemTouchToMove;

    public void setOnItemTouchToMove(OnItemTouchToMove onItemTouchToMove) {
        this.onItemTouchToMove = onItemTouchToMove;
    }

    public UserListAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.view_user_item, parent, false);
    }

    @Override
    public void convert(final RecyclerHolder holder, UserEntity item, int position) {
        holder.setText(R.id.textview_user_item_name, "姓名：" + item.getName());
        holder.setText(R.id.textview_user_item_sex, "性别：" + item.getSex());
        holder.setText(R.id.textview_user_item_age, "年龄：" + item.getAge());

        holder.getView(R.id.imageview).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (null != onItemTouchToMove) {
                    onItemTouchToMove.TouchToMove(holder);
                    return true;
                }
                return false;
            }
        });
    }

    public static interface OnItemTouchToMove {
        void TouchToMove(RecyclerHolder holder);
    }
}