package com.android.beaconyx.yesdexproject.MapPackage;

import java.io.Serializable;

/**
 * Created by beaconyx on 2017-11-01.
 */

public class FindBeaconContentsModel implements Serializable {

    final String FindBeaconContentsModel_KEY = "FindBeaconContentsModel";

    //찾은 비콘의 데이터 추가되야됨
    private String cpyTitle;
    private String cpyAddress;
    private String cpyHomePage;
    private String spyProductExp;//제품소개
    private String cpyExp;//회사소개
    private String cpyBeaconMinor;
    private String cpyTopImg;
    private String cpyThumbnailImg;
    private String cpyContactNumber;

    public String getCpyContactNumber() {
        return cpyContactNumber;
    }

    public void setCpyContactNumber(String cpyContactNumber) {
        this.cpyContactNumber = cpyContactNumber;
    }

    public String getCpyTopImg() {
        return cpyTopImg;
    }

    public void setCpyTopImg(String cpyTopImg) {
        this.cpyTopImg = cpyTopImg;
    }

    public String getCpyThumbnailImg() {
        return cpyThumbnailImg;
    }

    public void setCpyThumbnailImg(String cpyThumbnailImg) {
        this.cpyThumbnailImg = cpyThumbnailImg;
    }

    public String getCpyBeaconMinor() {
        return cpyBeaconMinor;
    }

    public void setCpyBeaconMinor(String cpyBeaconMinor) {
        this.cpyBeaconMinor = cpyBeaconMinor;
    }

    public String getCpyTitle() {
        return cpyTitle;
    }

    public void setCpyTitle(String cpyTitle) {
        this.cpyTitle = cpyTitle;
    }

    public String getCpyAddress() {
        return cpyAddress;
    }

    public void setCpyAddress(String cpyAddress) {
        this.cpyAddress = cpyAddress;
    }

    public String getCpyHomePage() {
        return cpyHomePage;
    }

    public void setCpyHomePage(String cpyHomePage) {
        this.cpyHomePage = cpyHomePage;
    }

    public String getSpyProductExp() {
        return spyProductExp;
    }

    public void setSpyProductExp(String spyProductExp) {
        this.spyProductExp = spyProductExp;
    }

    public String getCpyExp() {
        return cpyExp;
    }

    public void setCpyExp(String cpyExp) {
        this.cpyExp = cpyExp;
    }
}
