package com.android.beaconyx.yesdexproject.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.MapPackage.MapInfoActivity;
import com.android.beaconyx.yesdexproject.R;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.AInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.BInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.CInfoActivity;
import com.android.beaconyx.yesdexproject.AttendPackage.DInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.EInfoActivity;
import com.android.beaconyx.yesdexproject.TabViewPagerPackage.FInfoActivity;

public class MainActivity extends Activity {
    private ThisApplication mThisApplication;

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

        mThisApplication = (ThisApplication) getApplicationContext();

        mThisApplication.startBeaconThread();

    }

    public void mapInfoActivityOperation(View view) {
        Intent intent = new Intent(this, MapInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

    }

    public void aInfoActivityOperation(View view) {
        Intent intent = new Intent(this, AInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void bInfoActivityOperation(View view) {
        Intent intent = new Intent(this, BInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

    }


    public void cInfoActivityOperation(View view) {
        Intent intent = new Intent(this, CInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void dInfoActivityOperation(View view) {
        //TB_User_Ko 테이블의 USR_USER_ID를 체크 undefined 라면 강의 출석 인증 화면 창(DInfoActivity)로 이동
        //uuid는 앱 새로 다운시 SharedPreferences에서 가지고 있음

        //아니면 바로 AttendInfoActivity로 이동**
        Intent intent = new Intent(this, DInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void eInfoActivityOperation(View view) {
        Intent intent = new Intent(this, EInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void fInfoActivityOperation(View view) {
        Intent intent = new Intent(this, FInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
