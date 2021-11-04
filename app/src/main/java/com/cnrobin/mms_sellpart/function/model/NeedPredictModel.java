package com.cnrobin.mms_sellpart.function.model;

import android.os.Handler;
import android.os.Message;

import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.presenter.NeedPredicePresenterImpl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cnrobin on 17-12-19.
 * Just Enjoy It!!!
 */

public class NeedPredictModel {
    private NeedPredicePresenterImpl presenter;
    private List<ClothInfo> clothInfoList = new ArrayList<>();
    private static final String baseURL = "http://8.130.24.171:8080/GoodSystemServer/";
    private Handler handler = new MyHandler(this);

    public NeedPredictModel(NeedPredicePresenterImpl presenter) {
        this.presenter = presenter;
    }

    public void getNeedPredict() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<List<ClothInfo>> call = retrofitService.getNeedPredict();
        call.enqueue(new Callback<List<ClothInfo>>() {
            @Override
            public void onResponse(Call<List<ClothInfo>> call, Response<List<ClothInfo>> response) {
                if (response.code() == 200) {
                    clothInfoList = response.body();
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onFailure(Call<List<ClothInfo>> call, Throwable t) {

            }
        });
    }

      static class MyHandler extends Handler {
        WeakReference<NeedPredictModel> mNeedPredictModelWeakReference;
        public MyHandler(NeedPredictModel needPredictModel){
            this.mNeedPredictModelWeakReference = new WeakReference(needPredictModel);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                   mNeedPredictModelWeakReference.get().presenter.showNeedPredict(mNeedPredictModelWeakReference.get().clothInfoList);
                    break;

            }
        }
    }
}
