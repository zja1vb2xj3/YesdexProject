package com.android.beaconyx.yesdexproject.CheckPackage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.beaconyx.yesdexproject.Constant.ConstantPool;
import com.android.beaconyx.yesdexproject.R;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * AccountActivity 디바이스 최초 앱 실행 시 블루투스 UUID를 생성하여 Account Table에 업데이트
 */
public class AccountActivity extends Activity {

    private String CLASSNAME = getClass().getSimpleName();
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mPref = getSharedPreferences(ConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String uuid = getDeviceUUID();

        Log.i(CLASSNAME, uuid);
    }

    private AccountDtoModel userRegistInParse(){
        AccountDtoModel dtoModel = new AccountDtoModel();

        dtoModel.setACT_USER_ID(getDeviceUUID());//UUID

        dtoModel.setACT_CERTIFICATION(String.valueOf(false));
        dtoModel.setACT_OS_VERSION("Android");
        dtoModel.setACT_OS_VERSION(Build.VERSION.RELEASE);
        dtoModel.setACT_USER_PHONE_MODEL(Build.MODEL.toUpperCase());

        return dtoModel;
    }

    private String getDeviceUUID(){
        UUID deviceUUID;
        String savedUserId = mPref.getString(ConstantPool.ACCOUNT_SHARED_PREFERENCES_KEY, "");

        if (savedUserId.equals("")) {//저장된 데이터가 없다면
            final String androidUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!androidUID.equals("")) {
                    deviceUUID = UUID.nameUUIDFromBytes(androidUID.getBytes("utf8"));
                }
                else {
                    final String anotherUID = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
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
        }

        else {
            return savedUserId;
        }

        return deviceUUID.toString();
    }
}
