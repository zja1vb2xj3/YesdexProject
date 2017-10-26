package com.android.beaconyx.yesdexproject.CheckPackage;

import com.android.beaconyx.yesdexproject.ParseController.ParseManager;

/**
 * Created by beaconyx on 2017-10-26.
 */

public class AccountParseThread extends Thread {

    private ParseManager _manager;
    private String _userId;

    public AccountParseThread(ParseManager manager, String userId) {
        _manager = manager;
        _userId = userId;
    }

    @Override
    public void run() {
//        _manager.loadCheckUser(_userId);
    }

}
