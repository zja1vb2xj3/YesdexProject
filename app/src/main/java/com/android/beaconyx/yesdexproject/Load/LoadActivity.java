package com.android.beaconyx.yesdexproject.Load;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.android.beaconyx.yesdexproject.AccountPackage.AccountActivity;
import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.Constant.SharedPreferencesConstantPool;
import com.android.beaconyx.yesdexproject.Main.MainActivity;
import com.android.beaconyx.yesdexproject.R;
import com.bumptech.glide.Glide;

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
        mParseController.setOnBeaconContentsCallback(onBeaconContentsCallback);
        ImageView imageView = findViewById(R.id.mainlogo);
        Glide.with(this).load(R.mipmap.main_symbol).into(imageView);

        BeaconContentsThread beaconContentsThread = new BeaconContentsThread(mParseController);
        beaconContentsThread.start();
    }

    //region 비콘 컨텐츠 받아오기 콜백
    LoadParseController.OnBeaconContentsCallback onBeaconContentsCallback = new LoadParseController.OnBeaconContentsCallback() {
        @Override
        public void onParse(boolean resultSign) {
            if (resultSign == true) {
                Log.i(CLASSNAME, "로딩 서버 접속 완료");
                checkRegistedUser();
            } else {
                Log.i(CLASSNAME, "서버에서 데이터 전송 오류");
            }
        }
    };
    //endregion

    private void checkRegistedUser() {
        mPreferences = getSharedPreferences(SharedPreferencesConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String objectId = mPreferences.getString(SharedPreferencesConstantPool.ACCOUNT_OBJECTID_KEY, null);
        String certifiValue = mPreferences.getString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, "false");
        String uuid = mThisApplication.getDeviceUUID();

        if (objectId == null) {//폰을 지웟다 다시설치
            Log.i(CLASSNAME, "object null");
            Log.i(CLASSNAME, certifiValue);

            CheckAccountRegistedThread checkAccountRegistedThread = new CheckAccountRegistedThread(mParseController, uuid);
            checkAccountRegistedThread.start();
        } else {//object null이 아님
            Log.i(CLASSNAME, "object not null");
            Log.i(CLASSNAME + " object : ", objectId);
            Log.i(CLASSNAME + " certifi : ", certifiValue);

            startMainActivity();
        }
    }

    //region
    LoadParseController.OnCheckRegistedDeviceCallback onCheckRegistedDeviceCallback = new LoadParseController.OnCheckRegistedDeviceCallback() {
        @Override
        public void onCheckDeviceUUID(String objectId, String certifivalue) {
            if (objectId == null) {
                //등록된 디바이스가 없음 false
                Log.i("device callback", "등록된 디바이스가 없음");

                SharedPreferences.Editor editor = mPreferences.edit();

                editor.putString(SharedPreferencesConstantPool.ACCOUNT_OBJECTID_KEY, objectId);
                editor.putString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, certifivalue);//Parse에서 받아온 CertifiCation 저장

                editor.commit();

                startAccountAcitivity();
            } else {
                //등록된 디바이스가 있음 단순저장
                Log.i("device callback", "등록된 디바이스가 있음");

                Log.i("로딩", certifivalue);
                SharedPreferences.Editor editor = mPreferences.edit();

                editor.putString(SharedPreferencesConstantPool.ACCOUNT_OBJECTID_KEY, objectId);
                editor.putString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, certifivalue);//Parse에서 받아온 CertifiCation 저장

                editor.commit();

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
