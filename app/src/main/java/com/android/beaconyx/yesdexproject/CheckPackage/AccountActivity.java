package com.android.beaconyx.yesdexproject.CheckPackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.android.beaconyx.yesdexproject.Constant.ConstantPool;
import com.android.beaconyx.yesdexproject.Loading.SplashActivity;
import com.android.beaconyx.yesdexproject.ParseController.ParseManager;
import com.android.beaconyx.yesdexproject.R;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * AccountActivity 디바이스 최초 앱 실행 시 블루투스 UUID를 생성하여 Account Table에 업데이트
 */
public class AccountActivity extends Activity {

    private String CLASSNAME = getClass().getSimpleName();
    private SharedPreferences mPref;
    private AccountParseThread mAccountParseThread;
    private ParseManager mParseManager;
    private String mUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mParseManager = new ParseManager();
        mPref = getSharedPreferences(ConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        mParseManager.setOnCheckRegisterdDeviceCallback(onCheckRegisterdDeviceCallback);
        mParseManager.setOnRegisterUserCallback(onRegisterUserCallback);

        mUUID = getDeviceUUID();

        String savedUserId = mPref.getString(ConstantPool.ACCOUNT_SHARED_PREFERENCES_KEY, "");
        Log.i(CLASSNAME, savedUserId);

        //SharedPreferences ConstantPool.ACCOUNT_SHARED_PREFERENCES_KEY로 저장된 데이터가 있으면 바로 로딩 실행
        if(!savedUserId.equals("")){
            startSplashActivity();
        }
    }

    /**
     * SignInButton 클릭
     * @param view
     */
    public void signInButtonClick(View view) {

        mAccountParseThread = new AccountParseThread(mParseManager, mUUID);
        mAccountParseThread.start();

    }

    ParseManager.OnCheckRegisterdDeviceCallback onCheckRegisterdDeviceCallback = new ParseManager.OnCheckRegisterdDeviceCallback() {
        @Override
        public void onCheckDevice(boolean resultSign) {
            if (resultSign == true) {//등록된 디바이스가 있다면
                startSplashActivity();
            } else {//없다면
                AccountDtoModel accountDtoModel = accountTableRegistData();
                mParseManager.registAccountUserData(accountDtoModel);//서버에 등록
            }
        }
    };

    ParseManager.OnRegisterUserCallback onRegisterUserCallback = new ParseManager.OnRegisterUserCallback() {
        @Override
        public void onRegistUser() {
            startSplashActivity();
        }
    };

    private void startSplashActivity(){
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        finish();
    }


    /**
     * 등록된 데이터가 없다면 onCheckRegisterdDeviceCallback 신호에 Account 테이블에 Insert 할 데이터 설정
     * @return
     */
    private AccountDtoModel accountTableRegistData() {
        AccountDtoModel dtoModel = new AccountDtoModel();

        dtoModel.setACT_USER_ID(getDeviceUUID());//UUID
        dtoModel.setACT_CERTIFICATION(String.valueOf(false));
        dtoModel.setACT_USER_OS("Android");
        dtoModel.setACT_OS_VERSION(Build.VERSION.RELEASE);
        dtoModel.setACT_USER_PHONE_MODEL(Build.MODEL.toUpperCase());

        return dtoModel;
    }

    /**
     * 디바이스 UUID를 생성 후 반환
     * @return deviceUUID
     */
    private String getDeviceUUID() {
        UUID deviceUUID;
        String savedUserId = mPref.getString(ConstantPool.ACCOUNT_SHARED_PREFERENCES_KEY, "");

        if (savedUserId.equals("")) {//저장된 데이터가 없다면
            final String androidUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!androidUID.equals("")) {
                    deviceUUID = UUID.nameUUIDFromBytes(androidUID.getBytes("utf8"));
                } else {
                    final String anotherUID = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    if (anotherUID != null) {
                        deviceUUID = UUID.nameUUIDFromBytes(anotherUID.getBytes("utf8"));
                    } else {
                        deviceUUID = UUID.randomUUID();
                    }
                }

                SharedPreferences.Editor editor = mPref.edit();
                editor.putString(ConstantPool.ACCOUNT_SHARED_PREFERENCES_KEY, deviceUUID.toString());

                editor.commit();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return savedUserId;
        }

        return deviceUUID.toString();
    }
}
