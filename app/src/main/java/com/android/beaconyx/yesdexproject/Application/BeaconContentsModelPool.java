package com.android.beaconyx.yesdexproject.Application;

import java.util.ArrayList;

/**
 * Created by beaconyx on 2017-11-01.
 */

public class BeaconContentsModelPool {
    private static BeaconContentsModelPool beaconContentsModelList;

    public static BeaconContentsModelPool getInstance(){
        if(beaconContentsModelList == null){
            beaconContentsModelList = new BeaconContentsModelPool();
        }

        return beaconContentsModelList;
    }

    ArrayList<BeaconContentsModel> beaconContentsModels = new ArrayList<>();

    public void setBeaconContentsModels(ArrayList<BeaconContentsModel> beaconContentsModels) {
        this.beaconContentsModels = beaconContentsModels;
    }

    public ArrayList<BeaconContentsModel> getBeaconContentsModels() {
        return beaconContentsModels;
    }
}
