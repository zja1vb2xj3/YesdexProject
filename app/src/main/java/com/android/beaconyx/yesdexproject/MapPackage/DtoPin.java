package com.android.beaconyx.yesdexproject.MapPackage;

import android.graphics.PointF;

/**
 * Created by beaconyx on 2017-10-13.
 */

public class DtoPin {
    private int pinId;
    private PointF mPointF;
    private int mNotifyYesResID;
    private int mNotifyNoResID;
    private boolean mNotify = false;
    private String mMajor;
    private String mMinor;

    public DtoPin(int pinId){
        this.pinId = pinId;
    }



    public int getPinId() {
        return pinId;
    }

    public void setPinId(int pinId) {
        this.pinId = pinId;
    }

    public PointF getPointF() {
        return mPointF;
    }

    public void setPointF(PointF mPointF) {
        this.mPointF = mPointF;
    }

    public int getNotifyYesResID() {
        return mNotifyYesResID;
    }

    public void setNotifyYesResID(int mNotifyYesResID) {
        this.mNotifyYesResID = mNotifyYesResID;
    }

    public int getNotifyNoResID() {
        return mNotifyNoResID;
    }

    public void setNotifyNoResID(int mNotifyNoResID) {
        this.mNotifyNoResID = mNotifyNoResID;
    }

    public boolean getNotify() {
        return mNotify;
    }

    public void setNotify(boolean mNotify) {
        this.mNotify = mNotify;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String mMajor) {
        this.mMajor = mMajor;
    }

    public String getMinor() {
        return mMinor;
    }

    public void setMinor(String mMinor) {
        this.mMinor = mMinor;
    }

}
