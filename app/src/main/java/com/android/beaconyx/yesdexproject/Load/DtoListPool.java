package com.android.beaconyx.yesdexproject.Load;

import java.util.ArrayList;

/**
 * Created by beaconyx on 2017-10-30.
 */

public class DtoListPool {
    private static DtoListPool dtoListPool;

    public static DtoListPool getInstance(){
        if(dtoListPool == null){
            dtoListPool = new DtoListPool();
        }
        return dtoListPool;
    }

    private ArrayList<BeaconContentsModel> beaconContentsModelArrayList = new ArrayList<>();

    void addBeaconContentsModelList(BeaconContentsModel model){
        beaconContentsModelArrayList.add(model);
    }

    public ArrayList<BeaconContentsModel> getBeaconContentsModelArrayList(){
        return beaconContentsModelArrayList;
    }

}
