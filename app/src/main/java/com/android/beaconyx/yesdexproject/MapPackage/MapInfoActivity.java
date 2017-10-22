package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;
import java.util.HashMap;

import pl.polidea.view.ZoomView;

public class MapInfoActivity extends Activity {

    private MapView mMapView;
    private ArrayList<DtoPin> mMapPinList;

    private final String ACTIVITY_NAME = "MapInfoActivity";
    private ThisApplication mThisApplication;

    private Point mDisplaySize;

    private int mMapWidth;
    private int mMapHeight;

    private MapViewThread mMapViewThread;

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
        zoomView.setOnTouchListener(onTouchListener);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(zoomView);

    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i(ACTIVITY_NAME, "ACTION_DOWN");
                    break;

                case MotionEvent.ACTION_UP:
                    Log.i(ACTIVITY_NAME, "ACTION_UP");
                    break;
            }

            return false;
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume");
        mThisApplication.setIsMapInfoActivity(true); // AttendActivity 실행신호

        mMapViewThread = new MapViewThread();
        mMapViewThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause");
        mThisApplication.setIsMapInfoActivity(false); // AttendActivity 실행신호

        mMapViewThread.stopThread();
        mMapViewThread = null;
    }

    private void mapViewInit(View view) {
        mMapView = (MapView) view.findViewById(R.id.mapview);

        mMapView.setImage(ImageSource.resource(R.drawable.map_sample_img));

        mMapPinList = new ArrayList<>();

        for (int i = 1; i <= 3; i++)
            mMapPinList.add(new DtoPin(i));

        mMapView.setPin(mMapPinList);

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

    private void drawAllMarker(final ArrayList<DtoPin> pins) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    class MapViewThread extends Thread {
        private Handler handler;
        private boolean runSign = false;

        MapViewThread() {
            handler = new Handler();
        }

        @Override
        public void run() {
            super.run();

            while (!runSign) {
                try {

                    this.sleep(1000);

                    int beaconMinor = mThisApplication.getBeaconMinor();

                    Log.i("Minor", String.valueOf(beaconMinor));

                    uiHandler();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.currentThread().interrupt();
                }
            }
        }

        private void stopThread() {
            runSign = true;
        }

        private void uiHandler() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Canvas canvas = new Canvas();

                    mMapView.draw(canvas);
                }
            });
        }
    }//end inner Class

}
