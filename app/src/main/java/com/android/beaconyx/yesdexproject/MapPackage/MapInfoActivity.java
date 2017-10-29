package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;

public class MapInfoActivity extends Activity {

    private MapView mMapView;
    private ArrayList<MapMarker> mMapPinList;

    private final String ACTIVITY_NAME = "MapInfoActivity";
    private ThisApplication mThisApplication;

    private int mMapWidth;
    private int mMapHeight;

    private MapViewThread mMapViewThread;

    private LinearLayout mListViewContainer;
    private ListView mMarkerInfoListView;
    private ImageView mListHideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);
        titleInit();

        mThisApplication = (ThisApplication) this.getApplicationContext();

        mMapView = (MapView) findViewById(R.id.mapview);

        mMapView.setImage(ImageSource.resource(R.mipmap.map_img));


        mListHideImage = (ImageView) findViewById(R.id.listHideImage);
        mListHideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listLayoutUp();
            }
        });

        mListViewContainer = (LinearLayout) findViewById(R.id.listContainer);


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume");
        mThisApplication.setIsMapInfoActivity(true); // AttendActivity 실행신호

        if (mMapViewThread == null) {
            mMapViewThread = new MapViewThread();
            mMapViewThread.start();
        }
    }//MapActivity화면 true 및 Thread start

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause");
        mThisApplication.setIsMapInfoActivity(false); // AttendActivity 실행신호

        if (mMapViewThread != null) {
            mMapViewThread.stopThread();
            mMapViewThread = null;
        }
    }//MapActivity화면 false 및 Thread stop


    MapView.OnMarkerTouchListener onMarkerTouchListener = new MapView.OnMarkerTouchListener() {
        @Override
        public void onMarkerTouch(MapMarker marker) {
            createListView();
        }
    };

    private void createListView() {
        Toast.makeText(getApplicationContext(), "마커 클릭", Toast.LENGTH_SHORT).show();

        MapInfoListViewAdapter listViewAdapter = new MapInfoListViewAdapter();
        listViewAdapter.addItem("A");
        listViewAdapter.addItem("B");
        listViewAdapter.addItem("C");
        listViewAdapter.addItem("D");

        mMarkerInfoListView = (ListView) findViewById(R.id.listview);

        mMarkerInfoListView.setAdapter(listViewAdapter);

        listLayoutDown();

    }

    private void listLayoutDown() {
        Animation animaion = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        animaion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mMarkerInfoListView.setVisibility(View.VISIBLE);
                mListHideImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mListHideImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mMarkerInfoListView.startAnimation(animaion);

    }

    private void listLayoutUp() {
        Animation animaion = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        animaion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mListHideImage.startAnimation(animation);
                mMarkerInfoListView.setVisibility(View.GONE);
                mListHideImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mMarkerInfoListView.startAnimation(animaion);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.i(ACTIVITY_NAME, String.valueOf(action));
        if (action == 0) {//ActionDown
            if (mMapViewThread != null) {
                mMapViewThread.stopThread();
                mMapViewThread = null;
            }
        }

        if (action == 1) {//ActionUP
            if (mMapViewThread == null) {
                mMapViewThread = new MapViewThread();
                mMapViewThread.start();
            }
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
