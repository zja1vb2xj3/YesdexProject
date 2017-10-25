package com.android.beaconyx.yesdexproject.ParsePackage;

import android.util.Log;

/**
 * Created by beaconyx on 2017-10-25.
 */

public class AttendParseThread extends Thread{
    private ParseManager mParseManager;
    private String CLASSNAME = getClass().getName();
    private String userName;
    private String userNumber;

    public AttendParseThread(ParseManager mParseManager, String userName, String userNumber) {
        this.mParseManager = mParseManager;
        this.userName = userName;
        this.userNumber = userNumber;
    }

    @Override
    public void run() {
        super.run();

        if(mParseManager != null){
            mParseManager.checkAuthentication(userName, userNumber);
        }
        else{
            Log.i(CLASSNAME, "ParseManager가 null임");
        }
    }
}
