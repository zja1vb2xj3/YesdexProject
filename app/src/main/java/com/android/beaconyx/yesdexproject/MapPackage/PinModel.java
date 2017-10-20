package com.android.beaconyx.yesdexproject.MapPackage;

import android.graphics.PointF;

/**
 * Created by beaconyx on 2017-10-13.
 */

public class PinModel {

    private PointF mPointF;
    private int mNotifyYesResID;
    private int mNotifyNoResID;
    private boolean mNotify = false;
    private String mMajor;
    private String mMinor;

    public PointF getmPointF() {
        return mPointF;
    }

    public void setmPointF(PointF mPointF) {
        this.mPointF = mPointF;
    }

    public int getmNotifyYesResID() {
        return mNotifyYesResID;
    }

    public void setmNotifyYesResID(int mNotifyYesResID) {
        this.mNotifyYesResID = mNotifyYesResID;
    }

    public int getmNotifyNoResID() {
        return mNotifyNoResID;
    }

    public void setmNotifyNoResID(int mNotifyNoResID) {
        this.mNotifyNoResID = mNotifyNoResID;
    }

    public boolean ismNotify() {
        return mNotify;
    }

    public void setmNotify(boolean mNotify) {
        this.mNotify = mNotify;
    }

    public String getmMajor() {
        return mMajor;
    }

    public void setmMajor(String mMajor) {
        this.mMajor = mMajor;
    }

    public String getmMinor() {
        return mMinor;
    }

    public void setmMinor(String mMinor) {
        this.mMinor = mMinor;
    }
}
