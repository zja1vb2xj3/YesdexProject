package com.android.beaconyx.yesdexproject.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.beaconyx.yesdexproject.R;

/**
 * Created by user on 2017-10-10.
 */

public class CustomMapView extends ImageView {

    private int mMapWidth;
    private int mMapHeight;

    public CustomMapView(Context context) {
        super(context);
    }

    public CustomMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundResource(R.drawable.map_sample_img);

    }


}
