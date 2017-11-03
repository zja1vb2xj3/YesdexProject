package com.android.beaconyx.yesdexproject.Main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.AttendPackage.AttendInfoActivity;
import com.android.beaconyx.yesdexproject.AttendPackage.DInfoActivity;
import com.android.beaconyx.yesdexproject.Constant.SharedPreferencesConstantPool;
import com.android.beaconyx.yesdexproject.MapPackage.MapInfoActivity;
import com.android.beaconyx.yesdexproject.R;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.AInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.BInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.CInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.EInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.FInfoActivity;
import com.bumptech.glide.Glide;

//
public class MainActivity extends Activity {
    private ThisApplication mThisApplication;
    private String CLASSNAME = getClass().getSimpleName();
    int ii = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 블루투스가 켜져있지 않으면 활성화
         */


        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
        ImageView mainBanner = findViewById(R.id.mainBanner);
        ImageView mainBannerComment = findViewById(R.id.mainBannerComment);

        Glide.with(this).load(R.mipmap.main_symbol).into(mainBanner);
        Glide.with(this).load(R.drawable.main_txt).into(mainBannerComment);

        mThisApplication = (ThisApplication) getApplicationContext();

    }

    public void mapInfoButton_onClick(View view) {
        Intent intent = new Intent(this, MapInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

    }

    public void aInfoButton_onClick(View view) {
        Intent intent = new Intent(this, AInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void bInfoButton_onClick(View view) {
        Intent intent = new Intent(this, BInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

    }


    public void cInfoButton_onClick(View view) {
        Intent intent = new Intent(this, CInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void dInfoButton_onClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        String certifiValue = sharedPreferences.getString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, "false");

        Log.i(CLASSNAME, certifiValue);

        if(certifiValue.equals("true")){
            startAttendActivity();
        }
        else{
            startDinfoActivity();
        }
    }

    /**
     * certification이 true일때
     */
    private void startAttendActivity(){
        Intent intent = new Intent(this, AttendInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    /**
     * certification이 false일때
     */
    private void startDinfoActivity(){
        Intent intent = new Intent(this, DInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void eInfoButton_onClick(View view) {
        Intent intent = new Intent(this, EInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void fInfoButton_onClick(View view) {
        Intent intent = new Intent(this, FInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
