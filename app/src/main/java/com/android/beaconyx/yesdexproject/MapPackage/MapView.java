package com.android.beaconyx.yesdexproject.MapPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.android.beaconyx.yesdexproject.Load.BeaconContentsModel;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by beaconyx on 2017-10-13.
 */

public class MapView extends SubsamplingScaleImageView {

    private ArrayList<BeaconContentsModel> mBeaconContentsModels = new ArrayList<>();
    private HashMap<String, BeaconContentsModel> mBeaconContentsHashMap = new HashMap<>();
    private ArrayList<Bitmap> mMarkerBitmapList = new ArrayList<>();
    private HashMap<String, Rect> mRectMarkerHashMap = new HashMap<String, Rect>();

    private int mMapViewWidth;
    private int mMapViewHeight;

    private Context mContext;

    final String CLASSNAME = getClass().getSimpleName();

    public MapView(Context context, AttributeSet attr) {
        super(context, attr);
        this.mContext = context;
    }

    //region OnMarkerTouchListener
    private OnMarkerTouchListener MarkerTouch;

    interface OnMarkerTouchListener {
        void onMarkerTouch(BeaconContentsModel beaconContentsModel);

    }

    void setOnMarkerTouchListener(OnMarkerTouchListener markerTouch) {
        MarkerTouch = markerTouch;
    }
    //endregion

    void setMarkerList(ArrayList<BeaconContentsModel> beaconContentsModels) {

        this.mBeaconContentsModels = beaconContentsModels;

        mBeaconContentsHashMap.clear();

        for (int i = 0; i < this.mBeaconContentsModels.size(); i++) {
            mBeaconContentsHashMap.put(mBeaconContentsModels.get(i).getBeaconID(), this.mBeaconContentsModels.get(i));
        }

        mMarkerBitmapList.clear();
        mRectMarkerHashMap.clear();

        createMapMarkerBitmap(mBeaconContentsModels);

        invalidate();
    }

    void setOriginalMapViewSIze(int width, int height) {
        mMapViewWidth = width;
        mMapViewHeight = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        invalidate();

        if (!isReady()) {
            return;
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (mMarkerBitmapList != null) {
            for (int i = 0; i < mMarkerBitmapList.size(); i++) {

                int pinViewWidth = getSWidth();
                int pinViewHeight = getSHeight();


                Log.i(CLASSNAME, "onDraw");
                Log.i(CLASSNAME, String.valueOf(pinViewWidth));
                Log.i(CLASSNAME, String.valueOf(pinViewHeight));

                double reScaleWidth = pinViewWidth / (double) mMapViewWidth;
                double reScaleHeight = pinViewHeight / (double) mMapViewHeight;

                PointF pointF = new PointF();

                pointF.set(mBeaconContentsModels.get(i).getMapPositionX(), mBeaconContentsModels.get(i).getMapPositionY());

                final float xx = (float) (pointF.x * reScaleWidth);
                final float yy = (float) (pointF.y * reScaleHeight);

                PointF pinF = sourceToViewCoord(new PointF(xx, yy));
//
//                Log.i(CLASSNAME, String.valueOf(pinF.x));
//                Log.i(CLASSNAME, String.valueOf(pinF.y));


                //마커 위치
                float pinX = (pinF.x - (mMarkerBitmapList.get(i).getWidth()) / 2);
                float pinY = (pinF.y - (mMarkerBitmapList.get(i).getHeight()));

//                pinF.y
                Rect rect = new Rect();

                rect.set(//터치 영역
                        (int) (pinX - (mMarkerBitmapList.get(i).getWidth()) * 2),
                        (int) (pinY - (mMarkerBitmapList.get(i).getHeight()) * 2),
                        (int) (pinX + (mMarkerBitmapList.get(i).getWidth()) * 2),
                        (int) (pinY + (mMarkerBitmapList.get(i).getHeight()) * 2)
//                        (int) (pinX - (mMarkerBitmapList.get(i).getWidth() / 1.2)),
//                        (int) (pinY - (mMarkerBitmapList.get(i).getHeight() / 1.2)),
//                        (int) (pinX + (mMarkerBitmapList.get(i).getWidth() / 1.2)),
//                        (int) (pinY + (mMarkerBitmapList.get(i).getHeight() / 1.2))
                );

                mRectMarkerHashMap.put(mBeaconContentsModels.get(i).getBeaconID(), rect);

                canvas.drawBitmap(mMarkerBitmapList.get(i), pinX, pinY, paint);//이미지 , x,y

            }
        }
    }

    private void createMapMarkerBitmap(ArrayList<BeaconContentsModel> markerList) {
        if (markerList != null) {
            for (int i = 0; i < markerList.size(); i++) {
                float density = getResources().getDisplayMetrics().densityDpi;

                Log.i("createMarker", String.valueOf(density));

                Paint paint = new Paint();
                paint.setAntiAlias(true);

                Bitmap onImage = BitmapFactory.decodeResource(getResources(), R.mipmap.on_marker_img);
                Bitmap offImage = BitmapFactory.decodeResource(getResources(), R.mipmap.off_marker_img);

                float markerWidth = (density / 50000f) * offImage.getHeight();
                float markerHeight = (density / 50000f) * offImage.getHeight();

                if (markerWidth < 20) {
                    markerWidth = 20;
                }

                if (markerHeight < 20) {
                    markerHeight = 20;
                }

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

                    BeaconContentsModel model = mBeaconContentsHashMap.get(key);
                    if (model != null) {
                        if (MarkerTouch != null) {
                            MarkerTouch.onMarkerTouch(model);
                        }
                        break;
                    }
                } else {

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
