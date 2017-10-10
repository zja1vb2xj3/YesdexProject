package com.android.beaconyx.yesdexproject.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.beaconyx.yesdexproject.CustomView.CustomMapView;
import com.android.beaconyx.yesdexproject.R;

public class MapInfoActivity extends AppCompatActivity {

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
