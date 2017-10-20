package com.android.beaconyx.yesdexproject.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;

public class SplashActivity extends Activity {


    private ThisApplication mThisApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startLoading();

        mThisApplication = (ThisApplication) this.getApplicationContext();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mThisApplication.measureDisplay(this);
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 500);
    }


}
