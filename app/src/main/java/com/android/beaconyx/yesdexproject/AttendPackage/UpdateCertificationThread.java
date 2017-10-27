package com.android.beaconyx.yesdexproject.AttendPackage;

/**
 * Created by beaconyx on 2017-10-27.
 */

class UpdateCertificationThread extends Thread {
    private AttendParseController mController;
    private String mUUID;

    public UpdateCertificationThread(AttendParseController controller, String uuid){
        mController = controller;
        mUUID = uuid;
    }

    @Override
    public void run() {
        super.run();
        mController.updateCertification(mUUID);
    }
}
