package com.android.beaconyx.yesdexproject.MapPackage;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by beaconyx on 2017-10-13.
 */

public class MarkerThread extends Thread {
    private boolean mIsThreadSign = false;
    private Handler mHandler;

    private ArrayList<MarkerModel> markerModels;

    public MarkerThread() {
        mHandler = new Handler();
    }

    @Override
    public void run() {
        while (!this.currentThread().isInterrupted()) {
            try {

                this.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
                this.currentThread().interrupt();
            }
        }
    }

    @Override
    public boolean isInterrupted() {
        return super.isInterrupted();
    }


}
