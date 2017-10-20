package com.android.beaconyx.yesdexproject.MapPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;

/**
 * Created by beaconyx on 2017-10-13.
 */

public class MapView extends SubsamplingScaleImageView {

    private PointF sPin;

    ArrayList<MapPin> mapPins;
    ArrayList<DrawPin> drawnPins;
    Context mContext;
    String tag = getClass().getSimpleName();

    public MapView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public MapView(Context context, AttributeSet attr) {
        super(context, attr);
        this.mContext = context;
        init();
    }

    public void setPins(ArrayList<MapPin> mapPins) {
        this.mapPins = mapPins;

        invalidate();
    }

    public void setPin(PointF pin) {
        this.sPin = pin;
    }

    public PointF getPin() {
        return sPin;
    }

    private void init() {
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {

        Toast.makeText(mContext, "hi", Toast.LENGTH_SHORT).show();
        super.setOnTouchListener(l);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during       setup.
        if (!isReady()) {
            return;
        }

        drawnPins = new ArrayList<>();

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float density = getResources().getDisplayMetrics().densityDpi;


        for (int i = 0; i < mapPins.size(); i++) {
            MapPin mPin = mapPins.get(i);
            //Bitmap bmpPin = Utils.getBitmapFromAsset(context, mPin.getPinImgSrc());
            Bitmap bmpPin = BitmapFactory.decodeResource(this.getResources(), R.drawable.off_img);

            float w = (density / 600) * bmpPin.getWidth();
            float h = (density / 600) * bmpPin.getHeight();
            bmpPin = Bitmap.createScaledBitmap(bmpPin, (int) w, (int) h, true);

            PointF vPin = sourceToViewCoord(mPin.getPoint());
            //in my case value of point are at center point of pin image, so we need to adjust it here

            float vX = vPin.x - (bmpPin.getWidth() / 2);
            float vY = vPin.y - bmpPin.getHeight();


            canvas.drawBitmap(bmpPin, vX, vY, paint);

            //add added pin to an Array list to get touched pin
            DrawPin dPin = new DrawPin();
            dPin.setStartX(mPin.getX() - w / 2);
            dPin.setEndX(mPin.getX() + w / 2);
            dPin.setStartY(mPin.getY() - h / 2);
            dPin.setEndY(mPin.getY() + h / 2);
            dPin.setId(mPin.getId());
            drawnPins.add(dPin);
        }
    }

    public int getPinIdByPoint(PointF point) {

        for (int i = drawnPins.size() - 1; i >= 0; i--) {
            DrawPin dPin = drawnPins.get(i);
            if (point.x >= dPin.getStartX() && point.x <= dPin.getEndX()) {
                if (point.y >= dPin.getStartY() && point.y <= dPin.getEndY()) {
                    return dPin.getId();
                }
            }
        }
        return -1; //negative no means no pin selected
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        Log.i("TouchEvent", event.actionToString(event.getAction()));
        Log.i("핀위치", "");


        return false;
    }


}
