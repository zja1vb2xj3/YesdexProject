package com.android.beaconyx.yesdexproject.Dto;

/**
 * Created by beaconyx on 2017-10-25.
 */

public class DtoUser{
    private String mUserName;
    private String mUserNumber;
    private int mIdx;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserNumber() {
        return mUserNumber;
    }

    public void setUserNumber(String mUserNumber) {
        this.mUserNumber = mUserNumber;
    }

    public int getIdx() {
        return mIdx;
    }

    public void setIdx(int mIdx) {
        this.mIdx = mIdx;
    }
}
