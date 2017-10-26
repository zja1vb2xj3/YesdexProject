package com.android.beaconyx.yesdexproject.AttendPackage;

/**
 * Created by beaconyx on 2017-10-26.
 */

import com.android.beaconyx.yesdexproject.ParseController.ParseManager;

/**
 * TB_User_Ko의 USR_USER_ID를 update하는 Thread
 */
public class UpdateUserIdThread extends Thread {
    private ParseManager mParseManager;
    private String mUUID;
    private String mUserNumber;

    public UpdateUserIdThread(ParseManager parseManager, String uuid, String userNumber) {
        mParseManager = parseManager;
        mUUID = uuid;
        mUserNumber = userNumber;
    }

    @Override
    public void run() {
        super.run();
        if(mParseManager != null){
            mParseManager.updateUSR_USER_ID(mUserNumber, mUUID);
        }
    }
}
