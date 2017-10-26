package com.android.beaconyx.yesdexproject.AttendPackage;

import android.util.Log;

import com.android.beaconyx.yesdexproject.ParseController.ParseManager;

/**
 * Created by beaconyx on 2017-10-26.
 */

public class UpdateCertificationThread extends Thread{
    private String mUUID;
    private ParseManager mParseManager;
    private String CLASSNAME = getClass().getSimpleName();

    public UpdateCertificationThread(ParseManager parseManager,String typeStrUUID) {
        mParseManager = parseManager;
        this.mUUID = typeStrUUID;
    }

    @Override
    public void run() {
        super.run();

        if(mParseManager != null){
            mParseManager.updateACT_CERTIFICATION(mUUID);
        }

        else{
            Log.i(CLASSNAME, "ParseManager가 null임");
        }
    }


}
