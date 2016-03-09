package com.jtech_jrecyclerviewdemo.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.jtech.jrecyclerview.JRecyclerView;
import com.jtech.jrecyclerview.OnItemClickListener;
import com.jtech.jrecyclerview.OnItemLongClickListener;
import com.jtech.jrecyclerview.OnLoadListener;
import com.jtech.jrecyclerview.RecyclerHolder;
import com.jtech.refreshlayout.RefreshLayout;
import com.jtech_jrecyclerviewdemo.R;
import com.jtech_jrecyclerviewdemo.adapter.MyLoadFooterAdapter;
import com.jtech_jrecyclerviewdemo.adapter.UserListAdapter;
import com.jtech_jrecyclerviewdemo.bean.UserEntity;
import com.jtech_jrecyclerviewdemo.presenter.UserListPresenter;
import com.jtech_jrecyclerviewdemo.view.UserListView;

import java.util.List;

/**
 * Created by wuxubaiyang on 2016/2/6.
 */
public class MainActivity extends Activity implements UserListView, OnLoadListener, RefreshLayout.OnRefreshListener, OnItemClickListener, OnItemLongClickListener, UserListAdapter.OnItemTouchToMove {
    private int pageIndex = 1;

    private MyLoadFooterAdapter myLoadFooterAdapter;
    private UserListPresenter userListPresenter;
    private UserListAdapter userListAdapter;
    private ItemTouchHelper itemTouchHelper;

    private JRecyclerView jRecyclerView;
    private RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //控件实例化
        jRecyclerView = (JRecyclerView) findViewById(R.id.jrecyclerview);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshlayout);
        //是否开启加载更多功能
        jRecyclerView.setLoadMore(true);
        //设置layoutmanager(也可以换成其余两种)
        jRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //实例化适配器
        userListAdapter = new UserListAdapter(this);
        myLoadFooterAdapter = new MyLoadFooterAdapter();
        //设置适配器(第二个可以设置也可以不设置,设置的话可以设置自定义的足部视图适配器,不设置的话则使用默认的适配器)
        jRecyclerView.setAdapter(userListAdapter, myLoadFooterAdapter);
        //设置监听
        jRecyclerView.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        jRecyclerView.setOnItemClickListener(this);
        userListAdapter.setOnItemTouchToMove(this);
        jRecyclerView.setOnItemLongClickListener(this);
        //添加滑动删除以及拖动换位
        ItemTouchHelper.Callback callback = new ItemTouchCallback();
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(jRecyclerView);
        //实例化数据控制
        userListPresenter = new UserListPresenter(this);
        //发起请求
        refreshLayout.startRefreshing();
    }


    @Override
    public void showLoadFail() {
        refreshLayout.refreshingComplete();
        jRecyclerView.setLoadFailState();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {
        refreshLayout.refreshingComplete();
        jRecyclerView.setLoadCompleteState();
    }

    @Override
    public void setData(List<UserEntity> userEntities) {
        userListAdapter.setDatas(userEntities, pageIndex != 1);
    }

    @Override
    public void onItemClick(RecyclerHolder holder, View view, int position) {
        Toast.makeText(this, "item " + position + " 点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(RecyclerHolder holder, View view, int position) {
        Toast.makeText(this, "item " + position + " 长点击", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        userListPresenter.loadData();
    }

    @Override
    public void loadMore() {
        pageIndex++;
        userListPresenter.loadData();
    }

    @Override
    public void TouchToMove(RecyclerHolder holder) {
        itemTouchHelper.startDrag(holder);
    }

    /**
     * 自定义滑动删除以及拖动换位
     */
    private class ItemTouchCallback extends ItemTouchHelper.Callback {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            userListAdapter.moveData(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            userListAdapter.removeData(viewHolder.getAdapterPosition());
        }
    }
}