package com.android.beaconyx.yesdexproject.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.beaconyx.yesdexproject.MapPackage.MapInfoActivity;
import com.android.beaconyx.yesdexproject.R;

public class MainActivity extends Activity {
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void mapInfoActivityOperation(View view) {
        Intent intent = new Intent(this, MapInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

    }

    public void aInfoActivityOperation(View view){
        Intent intent = new Intent(this, AInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void bInfoActivityOperation(View view){
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
