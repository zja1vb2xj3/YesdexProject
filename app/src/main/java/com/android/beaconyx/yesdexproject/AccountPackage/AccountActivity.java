package com.android.beaconyx.yesdexproject.AccountPackage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.beaconyx.yesdexproject.Activity.MainActivity;
import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.Constant.SharedPreferencesConstantPool;
import com.android.beaconyx.yesdexproject.R;

/**
 * AccountActivity 디바이스 앱 실행 시 블루투스 UUID를 생성하여 Account Table에 업데이트
 * AccountActivity는 앱 최초 다운 (앱 최초 실행 아님)
 */
public class AccountActivity extends Activity {

    private String CLASSNAME = getClass().getSimpleName();

    private AccountParseController mAccountParseController;

    private String mUUID;
    private ThisApplication mThisApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mThisApplication = (ThisApplication) getApplicationContext();

        mAccountParseController = new AccountParseController();

        mAccountParseController.setOnRegisterUserCallback(onRegistUserCallback);

        mUUID = mThisApplication.getDeviceUUID();

        Log.i(CLASSNAME, mUUID);
    }

    //region
    AccountParseController.OnRegistUserCallback onRegistUserCallback = new AccountParseController.OnRegistUserCallback() {
        @Override
        public void onRegistUser(String objectId, String certifiValue) {
            if(objectId != null && certifiValue != null){
                SharedPreferences preferences = getSharedPreferences(SharedPreferencesConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
                preferences.edit().putString(SharedPreferencesConstantPool.ACCOUNT_OBJECTID_KEY, objectId);
                preferences.edit().putString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, certifiValue);
                preferences.edit().commit();

                startMainActivity();
            }
            else{
                Log.i(CLASSNAME, "등록실패");
            }
        }
    };
    //endregion


    /**
     * SignInButton 클릭
     *
     * @param view
     */
    public void signInButtonClick(View view) {
        AccountDtoModel model = getAccountRegistData();
        RegistAccountThread registAccountThread = new RegistAccountThread(mAccountParseController, model);
        registAccountThread.start();
    }


    private void startMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


    /**
     * 등록된 데이터가 없다면 onCheckRegisterdDeviceCallback 신호에 Account 테이블에 Insert 할 데이터 설정
     *
     * @return
     */
    private AccountDtoModel getAccountRegistData() {
        AccountDtoModel dtoModel = new AccountDtoModel();

        dtoModel.setACT_USER_ID(mUUID);
        dtoModel.setACT_CERTIFICATION(String.valueOf(false));
        dtoModel.setACT_USER_OS("Android");
        dtoModel.setACT_OS_VERSION(Build.VERSION.RELEASE);
        dtoModel.setACT_USER_PHONE_MODEL(Build.MODEL.toUpperCase());

        return dtoModel;
    }

}
