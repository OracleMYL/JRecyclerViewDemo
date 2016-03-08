package com.jtech_jrecyclerviewdemo.presenter;

import com.jtech_jrecyclerviewdemo.bean.UserEntity;
import com.jtech_jrecyclerviewdemo.model.GetUserListIMPL;
import com.jtech_jrecyclerviewdemo.model.OnUserListListener;
import com.jtech_jrecyclerviewdemo.view.UserListView;

import java.util.List;

/**
 * �û����ݴ���
 * Created by wuxubaiyang on 2016/2/6.
 */
public class UserListPresenter {
    private GetUserListIMPL getUserListIMPL;
    private UserListView userListView;

    public UserListPresenter(UserListView userListView) {
        this.getUserListIMPL = new GetUserListIMPL();
        this.userListView = userListView;
    }

    /**
     * ��������
     */
    public void loadData() {
        userListView.showLoading();
        getUserListIMPL.getUserList(new OnUserListListener() {
            @Override
            public void getUserListSuccess(List<UserEntity> userEntityList) {
                userListView.setData(userEntityList);
                userListView.stopLoading();
            }

            @Override
            public void getUserListFail() {
                userListView.showLoadFail();
            }
        });
    }

}