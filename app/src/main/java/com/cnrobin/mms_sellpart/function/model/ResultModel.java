package com.cnrobin.mms_sellpart.function.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cnrobin.mms_sellpart.function.presenter.ResultPresenterImp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cnrobin on 17-11-22.
 * Just Enjoy It!!!
 */

public class ResultModel {
    private static final String TAG = "ResultModel";
    private static final String baseURL = "http://8.130.24.171:8080/GoodSystemServer/";
    private ResultPresenterImp presenter;
    private Retrofit retrofit;
    private RetrofitService service;
    private int temp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    presenter.setInfo(temp);
                    break;
                case 2:

            }
        }
    };


    public ResultModel(ResultPresenterImp presenter) {
        this.presenter = presenter;
    }

    public void getCount(String id, String size) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder().client(client).baseUrl(baseURL).build();
        service = retrofit.create(RetrofitService.class);
        Map<String, String> map = new HashMap<>();
        id = id.replace(" ", "");
        map.put("id", id);
        Log.d(TAG, "getCount: " + id);
        map.put("type", "4");
        map.put("size", size);
        Call call = service.getCount(map);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody body = (ResponseBody) response.body();
                Log.d(TAG, "onResponse: ");
                if (body != null) {
                    try {
                        temp = Integer.parseInt(body.string());
                        Log.d(TAG, "onResponse: " + temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(1);
                } else {
                    Log.d(TAG, "onResponse: body is null");
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public void setCount(String id, String count, String size) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        id = id.replace(" ", "");
        Log.d(TAG, "setCount: " + id);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder().client(client).baseUrl(baseURL).build();
        service = retrofit.create(RetrofitService.class);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("type", "3");
        map.put("size", size);
        map.put("addr", "28206");
        map.put("count", count);
        Call call = service.setCount(map);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handler.sendEmptyMessage(2);
                Log.d(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

}
