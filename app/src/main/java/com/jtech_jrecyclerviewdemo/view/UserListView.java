package com.jtech_jrecyclerviewdemo.view;

import com.jtech_jrecyclerviewdemo.bean.UserEntity;

import java.util.List;

/**
 * Created by wuxubaiyang on 2016/2/6.
 */
public interface UserListView {
    void showLoadFail();
    void setData(List<UserEntity> userEntities);
    void showLoading();
    void stopLoading();

}