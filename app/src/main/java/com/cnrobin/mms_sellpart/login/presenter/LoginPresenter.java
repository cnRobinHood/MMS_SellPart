package com.cnrobin.mms_sellpart.login.presenter;

import com.cnrobin.mms_sellpart.login.login_contect.LoginConnect;
import com.cnrobin.mms_sellpart.login.model.UserInfoModel;

/**
 * Created by cnrobin on 17-10-12.
 * Just Enjoy It!!!
 */

public class LoginPresenter implements LoginConnect.LoginPresenter {
    private static final String TAG = "LoginPresenter";
    private LoginConnect.LoginView mLoginView;
    private LoginConnect.PassDataInterface mPassDataInterface;
    private UserInfoModel mUserInfoModel = new UserInfoModel(this);

    public LoginPresenter(LoginConnect.LoginView mLoginView) {
        this.mLoginView = mLoginView;
    }


    @Override
    public void getPassCheck(String username) {
        mUserInfoModel.getUserInfo(username);

    }

    @Override
    public void start() {

    }

    public void setPassDataInterface(LoginConnect.PassDataInterface passDataInterface) {
        mPassDataInterface = passDataInterface;
        mLoginView.checkPassWd(mPassDataInterface.success());

    }
}
