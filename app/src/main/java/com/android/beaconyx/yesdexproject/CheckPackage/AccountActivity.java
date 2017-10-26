package com.android.beaconyx.yesdexproject.CheckPackage;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.beaconyx.yesdexproject.R;

public class AccountActivity extends AppCompatActivity {

    private String CLASSNAME = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    private void userRegistInParse(){
        AccountDtoModel dtoModel = new AccountDtoModel();

        dtoModel.setACT_OS_VERSION("Android");
        dtoModel.setACT_OS_VERSION(Build.VERSION.RELEASE);
        dtoModel.setACT_USER_PHONE_MODEL(Build.MODEL.toUpperCase());
    }
}
