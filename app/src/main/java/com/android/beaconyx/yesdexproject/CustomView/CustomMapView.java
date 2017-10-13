package com.android.beaconyx.yesdexproject.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by user on 2017-10-10.
 */

public class CustomMapView extends ImageView {

    private int mMapWidth;
    private int mMapHeight;
    private Point point;
    public CustomMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        point = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMapWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMapHeight = MeasureSpec.getSize(heightMeasureSpec);

        Log.i("CustomMap W", String.valueOf(mMapWidth));
        Log.i("CustomMap H", String.valueOf(mMapHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mMapWidth/2, mMapHeight/2, 100, paint);
    }


}
