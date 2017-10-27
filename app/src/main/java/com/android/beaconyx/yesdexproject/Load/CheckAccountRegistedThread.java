package com.android.beaconyx.yesdexproject.Load;

/**
 * Created by beaconyx on 2017-10-27.
 */

class CheckAccountRegistedThread extends Thread {
    private LoadParseController mController;
    private String mUUID;

    public CheckAccountRegistedThread(LoadParseController parseController, String uuid){
        mController = parseController;
        mUUID = uuid;
    }

    @Override
    public void run() {
        super.run();

        mController.checkAccountKoRegistedUserId(mUUID);
    }
}
