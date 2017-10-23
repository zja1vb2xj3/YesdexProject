package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;

import pl.polidea.view.ZoomView;

public class MapInfoActivity extends Activity {

    private MapView mMapView;
    private ArrayList<MapMarker> mMapPinList;

    private final String ACTIVITY_NAME = "MapInfoActivity";
    private ThisApplication mThisApplication;

    private int mMapWidth;
    private int mMapHeight;

    private MapViewThread mMapViewThread;

    private LinearLayout mListLayout;
    private ListView mMarkerInfoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);

        mThisApplication = (ThisApplication) this.getApplicationContext();

        Point displaySize = mThisApplication.measureDisplay(this);
        mMapWidth = (int) (displaySize.x / 1.1);
        mMapHeight = (int) (displaySize.y / 1.1);

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
        int width = container.getMinimumWidth();
        int height = container.getMinimumHeight();

        Log.i("containerWidth", String.valueOf(width));
        Log.i("containerHeight", String.valueOf(height));
        container.addView(zoomView);

        mMarkerInfoListView = (ListView) findViewById(R.id.listview);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume");
        mThisApplication.setIsMapInfoActivity(true); // AttendActivity 실행신호

        mMapViewThread = new MapViewThread();
        mMapViewThread.start();
    }//MapActivity화면 true 및 Thread start

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause");
        mThisApplication.setIsMapInfoActivity(false); // AttendActivity 실행신호

        mMapViewThread.stopThread();
        mMapViewThread = null;
    }//MapActivity화면 false 및 Thread stop

    private void mapViewInit(View view) {
        mMapView = (MapView) view.findViewById(R.id.mapview);

        mMapView.setImage(ImageSource.resource(R.drawable.map_sample_img));

        mMapPinList = new ArrayList<>();

        mMapPinList.add(new MapMarker(String.valueOf(1)));

        mMapView.setMarker(mMapPinList);

        mMapView.setOnMarkerTouchListener(onMarkerTouchListener);


    }//mapView 바인딩, 이미지 설정, 마커 설정


    MapView.OnMarkerTouchListener onMarkerTouchListener = new MapView.OnMarkerTouchListener() {
        @Override
        public void onMarkerTouch(MapMarker marker) {
            createListView();
        }
    };

    private void createListView(){
        Toast.makeText(getApplicationContext(), "마커 클릭", Toast.LENGTH_SHORT).show();

        mListLayout = (LinearLayout) findViewById(R.id.listLayout);
        mListLayout.setVisibility(View.VISIBLE);

        MapInfoListViewAdapter listViewAdapter = new MapInfoListViewAdapter();
        listViewAdapter.addItem("A");
        listViewAdapter.addItem("B");
        listViewAdapter.addItem("C");
        listViewAdapter.addItem("D");

        mMarkerInfoListView.setAdapter(listViewAdapter);
    }

    public void listHideImage(View view) {
        mListLayout.setVisibility(View.INVISIBLE);
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.i("disPatch", String.valueOf(ev.getAction()));
        if (action == 0) {//ActionDown
            mMapViewThread.stopThread();
            mMapViewThread = null;
        }

        if (action == 1) {//ActionUP
            mMapViewThread = new MapViewThread();
            mMapViewThread.start();
        }
        return super.dispatchTouchEvent(ev);
    }//화면 터치 스레드 동작

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


    private class MapViewThread extends Thread {
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

                    this.sleep(4000);

                    int beaconMinor = mThisApplication.getBeaconMinor();

//                    Log.i("Minor", String.valueOf(beaconMinor));

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
    }//end inner Class MapView Thread 동작 (마커 비콘반응)

}
