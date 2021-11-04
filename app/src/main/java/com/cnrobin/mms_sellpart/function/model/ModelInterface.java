package com.cnrobin.mms_sellpart.function.model;

import com.cnrobin.mms_sellpart.function.entity.ClothInfo;

import java.util.List;

/**
 * Created by cnrobin on 17-10-19.
 * Just Enjoy It!!!
 */

public interface ModelInterface {
    void getclothInfoByKind(String kind, String pages, String type);

    boolean addclothInfo(ClothInfo clothInfo);

    List<String> getCount();

    void addImg(String Url);
}
