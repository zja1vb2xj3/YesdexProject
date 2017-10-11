package com.android.beaconyx.yesdexproject.Activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.beaconyx.yesdexproject.CustomView.CustomMapView;
import com.android.beaconyx.yesdexproject.R;

public class MapInfoActivity extends Activity {

    private Point mDrawScreenPosition;
    private CustomMapView mMapImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);

        mMapImageView = (CustomMapView) findViewById(R.id.mapImage);

        mMapImageView.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        int width = mMapImageView.getMeasuredWidth();
        int height = mMapImageView.getMeasuredHeight();


    }




}
