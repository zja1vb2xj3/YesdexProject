package com.android.beaconyx.yesdexproject.Load;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.beaconyx.yesdexproject.AccountPackage.AccountActivity;
import com.android.beaconyx.yesdexproject.Activity.MainActivity;
import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.Constant.SharedPreferencesConstantPool;
import com.android.beaconyx.yesdexproject.R;

public class LoadActivity extends Activity {
    private String CLASSNAME = getClass().getSimpleName();
    private ThisApplication mThisApplication;
    private SharedPreferences mPreferences;

    private LoadParseController mParseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        mThisApplication = (ThisApplication) this.getApplicationContext();

        mParseController = new LoadParseController();
        mParseController.setOnCheckRegisterdDeviceCallback(onCheckRegistedDeviceCallback);

        mPreferences = getSharedPreferences(SharedPreferencesConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String objectId = mPreferences.getString(SharedPreferencesConstantPool.ACCOUNT_OBJECTID_KEY, null);

        if (objectId == null) {//
            String uuid = mThisApplication.getDeviceUUID();
            Log.i(CLASSNAME, uuid);

            CheckAccountRegistedThread checkAccountRegistedThread = new CheckAccountRegistedThread(mParseController, uuid);
            checkAccountRegistedThread.start();
        } else {
            startMainActivity();
        }
    }

    //region
    LoadParseController.OnCheckRegistedDeviceCallback onCheckRegistedDeviceCallback = new LoadParseController.OnCheckRegistedDeviceCallback() {
        @Override
        public void onCheckDeviceUUID(String objectId, String certifivalue) {
            if (objectId == null) {
                //등록된 디바이스가 없음 false
                startAccountAcitivity();
            } else {
                //등록된 디바이스가 있음 단순저장
                mPreferences.edit().putString(SharedPreferencesConstantPool.ACCOUNT_OBJECTID_KEY, objectId);
                mPreferences.edit().putString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, certifivalue);
                mPreferences.edit().commit();

                startMainActivity();
            }
        }
    };
    //endregion

    private void startAccountAcitivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                finish();
            }
        }, 500);
    }

    private void startMainActivity() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 500);

    }


    @Override
    protected void onResume() {
        super.onResume();

        mThisApplication.measureDisplay(this);
    }


}
