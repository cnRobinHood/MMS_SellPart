package com.cnrobin.mms_sellpart.function.model;

import com.cnrobin.mms_sellpart.function.entity.ClothInfo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by cnrobin on 17-10-19.
 * Just Enjoy It!!!
 */

public interface RetrofitService {
    @GET("clothinfo")
    Call<List<ClothInfo>> getclothInfoByKind(@QueryMap Map<String, String> parmas);

    @GET("clothinfo")
    Call<ResponseBody> addClothInfo(@QueryMap Map<String, String> parmas);

    @GET("repertory")
    Call<ResponseBody> getCount(@QueryMap Map<String, String> parmas);

    @GET("repertory")
    Call<ResponseBody> setCount(@QueryMap Map<String, String> parmas);

    @GET("repertory")
    Call<ResponseBody> setNewCloseCount(@QueryMap Map<String, String> parmas);

    @GET("needpredict")
    Call<List<ClothInfo>> getNeedPredict();


    @Multipart
    @POST("clothinfo")
    Call<ResponseBody> setclothInfo(@Part MultipartBody.Part part);
}
