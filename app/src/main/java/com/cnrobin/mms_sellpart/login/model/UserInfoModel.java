package com.cnrobin.mms_sellpart.login.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cnrobin.mms_sellpart.login.login_contect.LoginConnect;
import com.cnrobin.mms_sellpart.login.presenter.LoginPresenter;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    private static final String baseUrl = "http://8.130.24.171:8080/GoodSystemServer/";
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
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String url = request.url().toString();
                String logStr = url;
                Log.d("Constant",  " request url: " + logStr);
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .addInterceptor(interceptor);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

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
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String url = request.url().toString();
                String logStr = url;
                Log.d("Constant",  " request url: " + logStr);
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .addInterceptor(interceptor);
        OkHttpClient client = builder.build();
        final Integer result;
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(baseUrl).build();
        Log.d(TAG, "registerCount: "+baseUrl);
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
                Log.d(TAG, "onResponse: success");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onFailure: "+"regisiter");
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
        return 1;
    }
}
