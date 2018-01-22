package com.cnrobin.mms_sellpart.login.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cnrobin.mms_sellpart.login.login_contect.LoginConnect;
import com.cnrobin.mms_sellpart.login.presenter.LoginPresenter;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cnrobin on 17-10-16.
 * Just Enjoy It!!!
 */

public class UserInfoModel {
    private static final String TAG = "UserInfoModel";
    private static final String baseUrl = "http://123.207.8.147:8080/GoodsSystemServe/";
    private User user;
    private LoginPresenter mLoginPresenter;
    public Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                mLoginPresenter.setPassDataInterface(new LoginConnect.PassDataInterface() {
                    @Override
                    public User success() {
                        return user;
                    }

                    @Override
                    public void error() {

                    }
                });
            }
            return false;
        }

    });

    public UserInfoModel(LoginPresenter mLoginPresenter) {
        this.mLoginPresenter = mLoginPresenter;
    }

    public void getUserInfo(String username) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        APIInterface apiInterface = retrofit.create(APIInterface.class);
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("type", "1");
        hashMap.put("uname", username);
        Call<User> call = apiInterface.getUserInfo(hashMap);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                setUser(user);
                handler.sendEmptyMessage(1);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });


    }

    private User getUser() {
        return this.user;

    }

    public void setUser(User user) {
        this.user = user;
    }

    public int registerCount(User user) {
        final Integer result;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
        APIInterface apiInterface = retrofit.create(APIInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "3");
        hashMap.put("uname", user.getUsername());
        hashMap.put("passwd", user.getPsword());
        hashMap.put("focus", user.getFoucs());
        hashMap.put("phoneNum", user.getPhoneNum());
        Call call = apiInterface.setUserInfo(hashMap);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        return 1;
    }
}
