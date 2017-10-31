package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.Load.BeaconContentsModel;
import com.android.beaconyx.yesdexproject.Load.DtoListPool;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;

public class MapInfoActivity extends Activity {

    private MapView mMapView;

    private final String ACTIVITY_NAME = "MapInfoActivity";
    private ThisApplication mThisApplication;

    private static final int MAPWIDTH = 1500;
    private static final int MAPHEIGHT = 2481;

    private ArrayList<BeaconContentsModel> mapMarkerModels;

    private ListView mMarkerInfoListView;
    private ImageView mListHideImage;

    private String CLASSNAME = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);

        mThisApplication = (ThisApplication) this.getApplicationContext();

        titleInit();

        mapInit();

        mListHideImage = (ImageView) findViewById(R.id.listHideImage);
        mListHideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listLayoutUp();
            }
        });

    }

    private void mapInit() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMapView = (MapView) findViewById(R.id.mapview);
                mMapView.setOnMarkerTouchListener(onMarkerTouchListener);
                Display display = getWindowManager().getDefaultDisplay();

                Point displaySize = new Point();

                display.getSize(displaySize);

                mMapView.setOriginalMapViewSIze(2850, 4752);

                mMapView.setImage(ImageSource.resource(R.mipmap.map_img));

                //마커 데이터 매칭
                DtoListPool dtoListPool = DtoListPool.getInstance();
                mapMarkerModels = dtoListPool.getBeaconContentsModelArrayList();

                mMapView.setMarkerList(mapMarkerModels);

                mMapView.invalidate();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume");
        mThisApplication.startBeaconThread();

    }//MapActivity화면 true 및 Thread start

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause");
        mThisApplication.stopBeaconThread(); // AttendActivity 실행신호

    }//MapActivity화면 false 및 Thread stop


    MapView.OnMarkerTouchListener onMarkerTouchListener = new MapView.OnMarkerTouchListener() {
        @Override
        public void onMarkerTouch(BeaconContentsModel model) {
            createListView();
        }
    };

    private void createListView() {

        MapInfoListViewAdapter listViewAdapter = new MapInfoListViewAdapter();
        listViewAdapter.addItem(".");
        listViewAdapter.addItem(".");
        listViewAdapter.addItem(".");
        listViewAdapter.addItem(".");

        mMarkerInfoListView = (ListView) findViewById(R.id.listview);

        mMarkerInfoListView.setAdapter(listViewAdapter);

        mMarkerInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MarkerInfoActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        listLayoutDown();

    }

    private void listLayoutDown() {
        Animation animaion = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        animaion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mMarkerInfoListView.setVisibility(View.VISIBLE);
                mListHideImage.setVisibility(View.INVISIBLE);
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
                mMarkerInfoListView.setVisibility(View.INVISIBLE);
                mListHideImage.setVisibility(View.INVISIBLE);
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
