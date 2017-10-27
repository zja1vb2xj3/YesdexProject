package com.android.beaconyx.yesdexproject.AttendPackage;

import android.util.Log;

/**
 * Created by beaconyx on 2017-10-25.
 */

public class CheckRegistedUserThread extends Thread{
    private AttendParseController mParseController;
    private String CLASSNAME = getClass().getName();
    private String mUserName;
    private String mUserNumber;
    private String mUUID;

    public CheckRegistedUserThread(AttendParseController controller, String userName, String userNumber, String uuid) {
        this.mParseController = controller;
        this.mUserName = userName;
        this.mUserNumber = userNumber;
        this.mUUID = uuid;
    }

    @Override
    public void run() {
        super.run();

        if(mParseController != null){
            mParseController.checkRegistUser(mUserName, mUserNumber, mUUID);
        }
        else{
            Log.i(CLASSNAME, "mParseController is null");
        }
    }
}
