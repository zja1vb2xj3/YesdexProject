package com.android.beaconyx.yesdexproject.Application;

/**
 * Created by beaconyx on 2017-11-01.
 */

public class SearchBeaconContentsListThread extends Thread{
    private BeaconParseController beaconParseController;
    private String beaconId;

    public SearchBeaconContentsListThread(BeaconParseController controller, String beaconId){
        beaconParseController = controller;
        this.beaconId = beaconId;
    }

    @Override
    public void run() {
        super.run();
        beaconParseController.findBeaconContentsList(beaconId);
    }
}
