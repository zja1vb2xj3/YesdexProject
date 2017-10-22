package com.android.beaconyx.yesdexproject.MapPackage;

import android.content.Context;
import android.content.pm.ApplicationInfo;
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
import android.widget.Toast;

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

    private ArrayList<DtoPin> mDtoPinList = new ArrayList<>();
    private HashMap<String, DtoPin> mDtoPinHashMap = new HashMap<>();
    private ArrayList<Bitmap> mPinBitmapList = new ArrayList<>();
    private int mOriginImageWidth = 0;
    private int mOriginImageHeight = 0;
    private HashMap<String, Rect> mRectList = new HashMap<String, Rect>();

    private Context mContext;
    String tag = getClass().getSimpleName();

    public MapView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public MapView(Context context, AttributeSet attr) {
        super(context, attr);
        this.mContext = context;
    }


    public void setPin(ArrayList<DtoPin> pinArrayList) {
        mDtoPinHashMap.clear();
        mDtoPinList = pinArrayList;

        for (int i = 0; i < mDtoPinList.size(); i++) {
            mDtoPinHashMap.put(mDtoPinList.get(i).getMajor() + "-" + mDtoPinList.get(i).getMinor(), mDtoPinList.get(i));
        }
//
//        mPinIconList.clear();
//        mRectList.clear();

        invalidate();
    }


    @Override
    public void setOnTouchListener(OnTouchListener l) {

        Toast.makeText(mContext, "hi", Toast.LENGTH_SHORT).show();
        super.setOnTouchListener(l);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("MapView", "onDraw");
        // Don't draw pin before image is ready so it doesn't move around during       setup.
        if (!isReady()) {
            return;
        }

        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int centerX = width / 2;
        int centerY = height / 2;

        int markerWidth = width / 30;
        int markerHeight = height / 60;

        Bitmap onImage = BitmapFactory.decodeResource(getResources(), R.drawable.on_img);
        Bitmap offImage = BitmapFactory.decodeResource(getResources(), R.drawable.off_img);

        Bitmap resizeOnBitmap = Bitmap.createScaledBitmap(onImage, markerWidth, markerHeight, true);
        Bitmap resizeOffBitmap = Bitmap.createScaledBitmap(offImage, markerWidth, markerHeight, true);

        canvas.drawBitmap(resizeOffBitmap, centerX - 100, centerY, paint);
        canvas.drawBitmap(resizeOffBitmap, centerX, centerY, paint);
        canvas.drawBitmap(resizeOffBitmap, centerX + 100, centerY, paint);

}

    private OnMarkerTouch mMarkerTouch;

public interface OnMarkerTouch {
    void onMarkerTouch(DtoPin dto);

}

    public void setOnMarkerTouchListener(OnMarkerTouch markerTouch) {
        mMarkerTouch = markerTouch;
    }


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {


        }

        return super.onTouchEvent(event);
    }


}
