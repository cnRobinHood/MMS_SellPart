package com.cnrobin.mms_sellpart.login.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnrobin on 17-10-17.
 * Just Enjoy It!!!
 */

public class User implements Parcelable {

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String username;
    private String psword;
    private String foucs;
    private String phoneNum;

    public User() {
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.psword = in.readString();
        this.foucs = in.readString();
        this.phoneNum = in.readString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsword() {
        return psword;
    }

    public void setPsword(String psword) {
        this.psword = psword;
    }

    public String getFoucs() {
        return foucs;
    }

    public void setFoucs(String foucs) {
        this.foucs = foucs;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.psword);
        dest.writeString(this.foucs);
        dest.writeString(this.phoneNum);
    }
}
