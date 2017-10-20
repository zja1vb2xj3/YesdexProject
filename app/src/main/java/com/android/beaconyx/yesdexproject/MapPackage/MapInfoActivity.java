package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;

public class MapInfoActivity extends Activity {

    private MapView mMapView;
    private ArrayList mapPinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);

        mapInit();
        titleInit();
    }

    private void mapInit(){
        mMapView = (MapView) findViewById(R.id.map_view);

        mMapView.setImage(ImageSource.bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_sample_img)));

        mapPinList = new ArrayList();

        mapPinList.add(new MapPin(200, 600, 1));
        mapPinList.add(new MapPin(300, 600, 2));
        mapPinList.add(new MapPin(400, 600, 3));

        mMapView.setPins(mapPinList);
    }

    private void titleInit(){
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
