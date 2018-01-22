package com.cnrobin.mms_sellpart.login.login_contect;

import com.cnrobin.mms_sellpart.BasePresenter;
import com.cnrobin.mms_sellpart.BaseView;
import com.cnrobin.mms_sellpart.login.model.User;

/**
 * Created by cnrobin on 17-10-12.
 * Just Enjoy It!!!
 */

public interface LoginConnect {
    interface LoginView extends BaseView {
        void checkPassWd(User user);

    }

    interface LoginPresenter extends BasePresenter {
        void getPassCheck(String username);

        int setUserCount(User user);
    }

    interface PassDataInterface {
        User success();

        void error();
    }
}
