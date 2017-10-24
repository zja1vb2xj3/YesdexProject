package com.android.beaconyx.yesdexproject.MapPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by beaconyx on 2017-10-13.
 */

public class MapView extends SubsamplingScaleImageView {

    private ArrayList<MapMarker> mMapMarkerList = new ArrayList<>();
    private HashMap<String, MapMarker> mapMarkerHashMap = new HashMap<>();
    private ArrayList<Bitmap> mMarkerBitmapList = new ArrayList<>();
    private HashMap<String, Rect> mRectMarkerHashMap = new HashMap<String, Rect>();

    private int mMapViewWidth;
    private int mMapViewHeight;

    private Context mContext;

    final String CLASSNAME = getClass().getSimpleName();

    private ThisApplication mThisApplication;

    public MapView(Context context, AttributeSet attr) {
        super(context, attr);
        this.mContext = context;
        mThisApplication = (ThisApplication) mContext.getApplicationContext();
    }

    private OnMarkerTouchListener MarkerTouch;

    public interface OnMarkerTouchListener {
        void onMarkerTouch(MapMarker marker);

    }

    public void setOnMarkerTouchListener(OnMarkerTouchListener markerTouch) {
        MarkerTouch = markerTouch;
    }


    public void setMarker(ArrayList<MapMarker> mapMarkerList) {

        mMapMarkerList = mapMarkerList;

        mapMarkerHashMap.clear();

        for (int i = 0; i < mMapMarkerList.size(); i++) {
            mapMarkerHashMap.put(mMapMarkerList.get(i).getMarkerId(), mMapMarkerList.get(i));
        }

        mMarkerBitmapList.clear();
        mRectMarkerHashMap.clear();

        createMapMarkerBitmap(mMapMarkerList);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.i(CLASSNAME, "onDraw");

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (mMarkerBitmapList != null) {
            for (int i = 0; i < mMarkerBitmapList.size(); i++) {
                mMapViewWidth = this.getMeasuredWidth();
                mMapViewHeight = this.getMeasuredHeight();

                int centerX = mMapViewWidth / 2;
                int centerY = mMapViewHeight / 2;

                Rect rect = new Rect();

                rect.set(
                        (int) (centerX - (mMarkerBitmapList.get(i).getWidth() / 1.2)),
                        (int) (centerY - (mMarkerBitmapList.get(i).getHeight() / 1.2)),
                        (int) (centerX + (mMarkerBitmapList.get(i).getWidth() / 1.2)),
                        (int) (centerY + (mMarkerBitmapList.get(i).getHeight() / 1.2))
                );

                mRectMarkerHashMap.put(mMapMarkerList.get(i).getMarkerId(), rect);

                canvas.drawBitmap(mMarkerBitmapList.get(i), centerX, centerY, paint);

                //                Log.i("sWidth", String.valueOf(width));
//                Log.i("sHeight", String.valueOf(height));
//                PointF markerPointF = sourceToViewCoord(new PointF(width, height));
//
//                float bitmapX = (markerPointF.x - (mMarkerBitmapList.get(i).getWidth() / 2));
//                float bitmapY = (markerPointF.y - (mMarkerBitmapList.get(i).getHeight()));
//
//                Log.i("bitmapX", String.valueOf(bitmapX));
//                Log.i("bitmapY", String.valueOf(bitmapY));
            }
        }

        invalidate(); // View 리셋
    }

    private void createMapMarkerBitmap(ArrayList<MapMarker> mapMarkerList) {
        if (mapMarkerList != null) {
            for (int i = 0; i < mapMarkerList.size(); i++) {
                float density = getResources().getDisplayMetrics().densityDpi;

                Paint paint = new Paint();
                paint.setAntiAlias(true);

                Bitmap onImage = BitmapFactory.decodeResource(getResources(), R.drawable.on_pin);
                Bitmap offImage = BitmapFactory.decodeResource(getResources(), R.drawable.off_pin);

                float markerWidth = (density / 420f) * offImage.getWidth();
                float markerHeight = (density / 420f) * offImage.getHeight();

                if (markerWidth < 50) {
                    markerWidth = 50;
                }

                if (markerHeight < 50) {
                    markerHeight = 50;
                }

//                Bitmap resizeOnBitmap = Bitmap.createScaledBitmap(onImage, markerWidth, markerHeight, true);
                Bitmap resizeOffBitmap = Bitmap.createScaledBitmap(offImage, (int) markerWidth, (int) markerHeight, true);
                mMarkerBitmapList.add(resizeOffBitmap);

            }//end for
        }//end if
    }// 맵 마커에 따른 비트맵 생성

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {

            Iterator<String> iter = mRectMarkerHashMap.keySet().iterator();

            while (iter.hasNext()) {
                String key = iter.next();
                Rect rect = mRectMarkerHashMap.get(key);
                float eventTouchX = event.getX();
                float eventTouchY = event.getY();
                if (rect.contains((int) eventTouchX, (int) eventTouchY)) {

                    MapMarker mapMarker = mapMarkerHashMap.get(key);
                    if (mapMarker != null) {
                        if (MarkerTouch != null) {
                            MarkerTouch.onMarkerTouch(mapMarker);

                        }
                        break;
                    }
                }

                else{

                }
            }

        }

        return super.onTouchEvent(event);
    }

    public int getMapViewWidth() {
        return mMapViewWidth;
    }

    public int getMapViewHeight() {
        return mMapViewHeight;
    }
}
