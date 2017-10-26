package com.android.beaconyx.yesdexproject.CheckPackage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by beaconyx on 2017-10-24.
 */

public class CheckSharedPreferencesManager {

    private SharedPreferences permissionData;

    private final String PERMISSIONDATA = "PERMISSIONDATA";

    private static final String PERMISSIONDATA_KEY = "PERMISSIONDATAKEY";

    public CheckSharedPreferencesManager(CheckActivity activity) {
        permissionData = activity.getSharedPreferences(PERMISSIONDATA, Context.MODE_PRIVATE);
    }

    public void save(boolean sign) {
        SharedPreferences.Editor editor = permissionData.edit();
        editor.putBoolean(PERMISSIONDATA_KEY, sign);

        editor.commit();

    }

    public boolean getCheckLocationSign() {

        boolean getCheckLocationSign = permissionData.getBoolean(PERMISSIONDATA_KEY, false);

        if (getCheckLocationSign == true) {
            return getCheckLocationSign;
        }

        return false;
    }
}
