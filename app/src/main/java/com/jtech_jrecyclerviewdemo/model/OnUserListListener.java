package com.jtech_jrecyclerviewdemo.model;

import com.jtech_jrecyclerviewdemo.bean.UserEntity;

import java.util.List;

/**
 * �û��б�����ص�
 * Created by wuxubaiyang on 2016/2/6.
 */
public interface OnUserListListener {
    void getUserListSuccess(List<UserEntity> userEntityList);

    void getUserListFail();
}