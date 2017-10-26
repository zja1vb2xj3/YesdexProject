package com.android.beaconyx.yesdexproject.AttendPackage;

import com.android.beaconyx.yesdexproject.ParseController.ParseManager;

/**
 * Created by beaconyx on 2017-10-27.
 */

public class SearchUSRUSERIDThread extends Thread {
    private ParseManager mParseManager;
    private String mUUID;

    public SearchUSRUSERIDThread(ParseManager mParseManager, String mUUID) {
        this.mParseManager = mParseManager;
        this.mUUID = mUUID;
    }

    @Override
    public void run() {
        super.run();

        if(mParseManager != null){
            mParseManager.searchUSR_USER_ID(mUUID);
        }
    }
}
