package com.cnrobin.mms_sellpart.function.model;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.presenter.BrowsePersenterImp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cnrobin on 17-10-19.
 * Just Enjoy It!!!
 */

public class ModelInterfaceImp implements ModelInterface {
    private static final String TAG = "ModelInterfaceImp";
    private static final String baseURL = "http://123.207.8.147:8080/GoodsSystemServe/";
    private List<ClothInfo> clothInfos = new ArrayList<>();
    private BrowsePersenterImp persenter;
    private Retrofit retrofit;
    private String kind;
    private String pages;
    private String type;
    private RetrofitService service;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    persenter.setContectPersenter(new FunctionContect.ModelContectPersenter() {
                        @Override
                        public List<ClothInfo> success() {
                            return clothInfos;
                        }

                        @Override
                        public void fail() {

                        }
                    });
                    break;
                case 2:
                    persenter.setSearchResult(clothInfos);

            }
        }
    };

    public ModelInterfaceImp(BrowsePersenterImp persenter) {
        this.persenter = persenter;
    }


    @Override
    public void getclothInfoByKind(String kind, String pages, String type) {
        this.kind = kind;
        this.pages = pages;
        this.type = type;
        clothInfos.clear();
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
        retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        service = retrofit.create(RetrofitService.class);
        Map<String, String> map = new HashMap<>();
        map.put("kind", kind);
        map.put("type", "1");
        map.put("type1", type);
        map.put("pages", pages);
        Call<List<ClothInfo>> call = service.getclothInfoByKind(map);
        getClothData(call);
    }

    void getClothData(Call call) {
        call.enqueue(new Callback<List<ClothInfo>>() {
            @Override
            public void onResponse(Call<List<ClothInfo>> call, Response<List<ClothInfo>> response) {
                if (response.code() == 500) {
                    getclothInfoByKind(kind, pages, type);
                } else {
                    clothInfos = response.body();
                    if (type.equals("0")) {
                        handler.sendEmptyMessage(1);
                    } else if (type.equals("1")) {
                        handler.sendEmptyMessage(2);
                    }

                }

            }

            @Override
            public void onFailure(Call<List<ClothInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean addclothInfo(ClothInfo clothInfo) {
        retrofit = new Retrofit.Builder().baseUrl(baseURL).build();
        service = retrofit.create(RetrofitService.class);
        Map<String, String> map = new HashMap<>();
        map.put("type", "3");
        map.put("id", clothInfo.getID());
        map.put("kind", clothInfo.getKinds());
        map.put("star", clothInfo.getStars());
        map.put("lining", clothInfo.getLining());
        map.put("season", clothInfo.getSeason());
        map.put("size", clothInfo.getSize());
        map.put("image", clothInfo.getImages());
        Call<ResponseBody> call = service.addClothInfo(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: 我失败了0.0");
            }
        });
        return true;

    }

    @Override
    public List<String> getCount() {
        return null;
    }

    @Override
    public void addImg(String Url) {
        retrofit = new Retrofit.Builder().baseUrl(baseURL).build();
        service = retrofit.create(RetrofitService.class);
        File file = new File(Url);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), imageBody);
        Call<ResponseBody> call = service.setclothInfo(imageBodyPart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "onResponse: " + "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });

    }

    public void setCount(String id, String count) {
        retrofit = new Retrofit.Builder().baseUrl(baseURL).build();
        service = retrofit.create(RetrofitService.class);
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("id", id);
        map.put("count", count);
        map.put("size", "XL");
        map.put("count", count);
        map.put("addr", "28206");
        Call call = service.setNewCloseCount(map);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }
}
