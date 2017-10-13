package com.android.beaconyx.yesdexproject.Activity;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.CustomView.CustomMapView;
import com.android.beaconyx.yesdexproject.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MapInfoActivity extends Activity {

    private Point mDrawScreenPosition;
    private CustomMapView mCustomMapView;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);

        mCustomMapView = (CustomMapView) findViewById(R.id.map_view);

        Drawable drawable = getResources().getDrawable(R.drawable.map_sample_img);
        mCustomMapView.setImageDrawable(drawable);

        mAttacher = new PhotoViewAttacher(mCustomMapView);

        titleInit();
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
