package com.android.beaconyx.yesdexproject.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

        Bitmap marker = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(marker);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.BLACK);
        canvas.drawCircle(50,50,10, paint);
    }


}
