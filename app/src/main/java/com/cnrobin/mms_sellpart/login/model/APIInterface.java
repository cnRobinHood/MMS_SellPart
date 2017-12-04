package com.cnrobin.mms_sellpart.login.model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by cnrobin on 17-10-16.
 * Just Enjoy It!!!
 */

public interface APIInterface {
    @GET("admininfo")
    Call<User> getUserInfo(@QueryMap Map<String, String> parmas);
}
