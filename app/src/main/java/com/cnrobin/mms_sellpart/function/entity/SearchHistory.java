package com.cnrobin.mms_sellpart.function.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by cnrobin on 17-11-30.
 * Just Enjoy It!!!
 */

public class SearchHistory extends DataSupport {
    private String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
