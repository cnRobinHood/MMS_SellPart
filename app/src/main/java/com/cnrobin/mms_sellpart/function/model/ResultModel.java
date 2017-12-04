package com.cnrobin.mms_sellpart.function.model;

import android.os.Handler;
import android.os.Message;

import com.cnrobin.mms_sellpart.function.presenter.ResultPresenterImp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
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
    private static final String baseURL = "http://123.207.8.147:8080/GoodsSystemServe/";
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

    public void getCount(String id) {
        retrofit = new Retrofit.Builder().baseUrl(baseURL).build();
        service = retrofit.create(RetrofitService.class);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("type", "4");
        map.put("size", "XL");
        Call call = service.getCount(map);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody body = (ResponseBody) response.body();
                try {
                    temp = Integer.parseInt(body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public void setCount(String id, String count) {
        retrofit = new Retrofit.Builder().baseUrl(baseURL).build();
        service = retrofit.create(RetrofitService.class);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("type", "3");
        map.put("size", "XL");
        map.put("addr", "28206");
        map.put("count", count);
        Call call = service.setCount(map);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

}
