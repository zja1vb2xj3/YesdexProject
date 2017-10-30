package com.android.beaconyx.yesdexproject.Load;

/**
 * Created by beaconyx on 2017-10-11.
 */

public class BeaconContentsModel {
    private int idx;
    private String beaconID;
    private String beaconMajor;
    private String beaconMinor;
    private int mapPositionX;
    private int mapPositionY;

    //region getter and setter


    public void setIdx(int idx) {
        this.idx = idx;
    }


    public void setBeaconID(String beaconID) {
        this.beaconID = beaconID;
    }


    public void setBeaconMajor(String beaconMajor) {
        this.beaconMajor = beaconMajor;
    }


    public void setBeaconMinor(String beaconMinor) {
        this.beaconMinor = beaconMinor;
    }

    public void setMapPositionX(int mapPositionX) {
        this.mapPositionX = mapPositionX;
    }

    public void setMapPositionY(int mapPositionY) {
        this.mapPositionY = mapPositionY;
    }

    public int getIdx() {
        return idx;
    }

    public String getBeaconID() {
        return beaconID;
    }

    public String getBeaconMajor() {
        return beaconMajor;
    }

    public String getBeaconMinor() {
        return beaconMinor;
    }

    public int getMapPositionX() {
        return mapPositionX;
    }

    public int getMapPositionY() {
        return mapPositionY;
    }

    //endregion
}
