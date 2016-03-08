package com.jtech_jrecyclerviewdemo.model;

import android.os.AsyncTask;

import com.jtech_jrecyclerviewdemo.bean.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户列表实现方法
 * Created by wuxubaiyang on 2016/2/6.
 */
public class GetUserListIMPL implements GetUserList {

    @Override
    public void getUserList(final OnUserListListener onUserListListener) {
        //发起模拟网络请求
        new AsyncTask<String, String, List<UserEntity>>() {
            @Override
            protected List<UserEntity> doInBackground(String... params) {
                try {
                    Thread.sleep(2200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<UserEntity> userEntities = new ArrayList<UserEntity>();
                for (int i = 0; i < 15; i++) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setName("小明 " + i + " 号");
                    userEntity.setAge(i + 20);
                    userEntity.setSex("男/女");
                    userEntities.add(userEntity);
                }
                return userEntities;
            }

            @Override
            protected void onPostExecute(List<UserEntity> userEntities) {
                if (null != onUserListListener) {
                    onUserListListener.getUserListSuccess(userEntities);
                }
            }
        }.execute("");
    }
}