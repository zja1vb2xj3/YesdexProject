package com.android.beaconyx.yesdexproject.CheckPackage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class PermissionActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION_CONTACTS = 0;


    private String CLASSNAME = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * 위치권한이 허용되어 있을때
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            startAccountActivity();

        }

        /**
         * 위치권한이 허용되어 있지 않을때
         */
        else {
            TedPermission.with(this)
                    .setPermissionListener(permissionListener)
                    .setRationaleMessage("앱을 이용하려면 위치정보 권한이 필요합니다.")
                    .setDeniedMessage("권한 요청을 취소 하셨습니다. 비콘 서비스를 이용할 수 없습니다.")
                    .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN)
                    .check();

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }

    private void startAccountActivity() {
        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
        finish();
    }


    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            Toast.makeText(getApplicationContext(), "권한 허용 ", Toast.LENGTH_SHORT).show();
            Log.i("check", "granted");
            int checkLocation = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            int checkBluetooth = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH);

            if (checkLocation == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getApplicationContext(), "위치정보 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }

            if (checkBluetooth == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getApplicationContext(), "블루투스 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }


        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한 거절.", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startAccountActivity();

                } else {

                    //권한 거부
                    Toast.makeText(getApplicationContext(), "권한을 거부하여 종료합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }
}
