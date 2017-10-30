package com.android.beaconyx.yesdexproject.Load;

/**
 * Created by beaconyx on 2017-10-30.
 */

public class BeaconContentsThread extends Thread {
    private LoadParseController mLoadParseController;

    public BeaconContentsThread(LoadParseController controller){
        this.mLoadParseController = controller;
    }

    @Override
    public void run() {
        super.run();
        mLoadParseController.loadBeaconContents();
    }
}
