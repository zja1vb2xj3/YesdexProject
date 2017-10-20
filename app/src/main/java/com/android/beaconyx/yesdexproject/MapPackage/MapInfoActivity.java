package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;

import pl.polidea.view.ZoomView;

public class MapInfoActivity extends Activity {

    private MapView mMapView;
    private ArrayList mMapPinList;

    private final String ACTIVITY_NAME = "MapInfoActivity";
    private ThisApplication mThisApplication;

    private Point mDisplaySize;

    private int mMapWidth;
    private int mMapHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);

        mThisApplication = (ThisApplication) this.getApplicationContext();

        mMapWidth = (int) (mThisApplication.getDisplaySize().x / 1.1);
        mMapHeight = (int) (mThisApplication.getDisplaySize().y / 1.1);

        titleInit();

        View mapView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map, null, false);

        Log.i("mMapWidth", String.valueOf(mMapWidth));
        Log.i("mMapHeight", String.valueOf(mMapHeight));

        mapViewInit(mapView);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mMapWidth, mMapHeight);

        ZoomView zoomView = new ZoomView(this);
        zoomView.setLayoutParams(layoutParams);
        zoomView.addView(mapView);
        zoomView.setMaxZoom(4f);

        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(zoomView);

    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume");
//        mThisApplication.setIsAttendActivityComplete(true); // AttendActivity 실행신호

    }


    private void mapViewInit(View view) {
        mMapView = (MapView) view.findViewById(R.id.mapview);

        mMapView.setImage(ImageSource.resource(R.drawable.map_sample_img));

        mMapPinList = new ArrayList();

        mMapPinList.add(new MapPin(mMapWidth / 2, mMapHeight / 2, 1));

        mMapView.setPins(mMapPinList);


    }

    private void titleInit() {
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.map_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
