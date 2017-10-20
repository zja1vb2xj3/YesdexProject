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
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by beaconyx on 2017-10-13.
 */

public class MapView extends SubsamplingScaleImageView {

    private ArrayList<DtoPin> mDtoPinList = new ArrayList<>();
    private HashMap<String, DtoPin> mDtoPinHashMap = new HashMap<>();
    private ArrayList<Bitmap> mPinIconList = new ArrayList<>();
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

    private void initialise() {
        if (mDtoPinList != null) {
            for (int i = 0; i < mDtoPinList.size(); i++) {
                if (mDtoPinList.get(i).getNotifyNoResID() != 0 && mDtoPinList.get(i).getNotifyYesResID() != 0) {

                    float density = getResources().getDisplayMetrics().densityDpi;
                    Bitmap bitmap = null;
                    if (mDtoPinList.get(i).getNotify() == true) {
                        bitmap = BitmapFactory.decodeResource(this.getResources(), mDtoPinList.get(i).getNotifyYesResID());
                    } else {
                        bitmap = BitmapFactory.decodeResource(this.getResources(), mDtoPinList.get(i).getNotifyNoResID());
                    }

                    float w = (density / 420f) * bitmap.getWidth();
                    float h = (density / 420f) * bitmap.getHeight();
                    if (w < 50) {
                        w = 50;
                    }
                    if (h < 50) {
                        h = 50;
                    }
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) w, (int) h, true);
                    mPinIconList.add(bitmap);
                }
            }
        }
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


        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (mPinIconList != null && mDtoPinList != null) {
            for (int i = 0; i < mPinIconList.size(); i++) {
                if (mDtoPinList.get(i).getPointF() != null) {

                    int pinViewWidth = this.getSWidth();
                    int pinViewHeight = this.getSHeight();

                    Log.d("TEST11", "swidth : " + pinViewWidth + ",  sheight : " + pinViewHeight);

                    double rescaleWidth = pinViewWidth / (double) mOriginImageWidth;
                    double rescaleHeight = pinViewHeight / (double) mOriginImageHeight;

                    PointF pointf = mDtoPinList.get(i).getPointF();
                    float xx = (float) (pointf.x * rescaleWidth);
                    float yy = (float) (pointf.y * rescaleHeight);


                    PointF vPin = sourceToViewCoord(new PointF(xx, yy));


                    float vX = (vPin.x - (mPinIconList.get(i).getWidth() / 2));
                    float vY = (vPin.y - mPinIconList.get(i).getHeight());

                    Rect rect = new Rect();

                    //좀더 빡빡하지 않게
                    rect.set(
                            (int) (vX - (mPinIconList.get(i).getWidth() / 1.2)),
                            (int) (vY - (mPinIconList.get(i).getHeight() / 1.2)),
                            (int) (vX + (mPinIconList.get(i).getWidth() / 1.2)),
                            (int) (vY + (mPinIconList.get(i).getHeight() / 1.2))
                    );

                    mRectList.put(mDtoPinList.get(i).getMajor() + "-" + mDtoPinList.get(i).getMinor(), rect);
                    canvas.drawBitmap(mPinIconList.get(i), vX, vY, paint);

                }
            }
        }
    }


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        Log.i("TouchEvent", event.actionToString(event.getAction()));
        Log.i("핀위치", "");


        return false;
    }



}
