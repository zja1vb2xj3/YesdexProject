package com.android.beaconyx.yesdexproject.Application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.beaconyx.yesdexproject.Dto.DtoContent;
import com.android.beaconyx.yesdexproject.Fragment.AttendDialogFragment1;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by user on 2017-10-15.
 */

public class ThisApplication extends Application implements BeaconConsumer, BootstrapNotifier {
    private BeaconManager mBeaconManager;
    private Region mRegion;

    public final int mIgnore_Rssi = -90;

    private AttendDialogFragment1 mDialogFragment1 = AttendDialogFragment1.newInstance();

    private final String CLASSNAME = "ThisApplication";

    private Point mDisplaySize = new Point();

    private boolean mIsAttendActivityComplete = false;
    private boolean mFragmentDialog1Sign = true;

    private ActivityManager mActivityManager;
    private FragmentActivity mMotionFragmentActivity = null;

    private boolean mIsMapInfoActivityComplete = false;

    private int mBeaconMinor;

    @Override
    public void onCreate() {
        super.onCreate();
        beaconInit();
        Log.i(CLASSNAME, "onCreate");
        Log.i("isAttendActivity", String.valueOf(mIsAttendActivityComplete));
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

    }

    private void callDialogFragment1() {
        if (mMotionFragmentActivity != null) {
            mMotionFragmentActivity.getFragmentManager().beginTransaction().remove(mDialogFragment1).commit();
            mDialogFragment1.show(mMotionFragmentActivity.getFragmentManager(), "fragment1");

            setFragmentDialog1Sign(false);
        }

        mDialogFragment1.setOnDialogFragment1CancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
                setFragmentDialog1Sign(true);
            }
        });
    }


    public Point getDisplaySize() {
        return mDisplaySize;
    }

    /**
     * 디바이스 width height 측정
     */
    public Point measureDisplay(Activity activity) {
        if (activity != null) {

            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            mDisplaySize.set(width, height);
            Log.i("measureDisplay width", String.valueOf(mDisplaySize.x));
            Log.i("measureDisplay height", String.valueOf(mDisplaySize.y));


        } else {
            Log.i("measureDisplay", "null");
        }

        return mDisplaySize;
    }


    /**
     * 최초 한번 mBeaconManager 객체, mRegion 객체 생성 및 초기설정
     */
    private void beaconInit() {
        mRegion = new Region("myRangingUniqueId", Identifier.parse("a0fabefc-b1f5-4836-8328-7c5412fff9c4"), Identifier.parse("51"), null);
        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.setAndroidLScanningDisabled(true);
        mBeaconManager.setBackgroundBetweenScanPeriod(1000);
        mBeaconManager.setForegroundBetweenScanPeriod(1000);
        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        mBeaconManager.bind(this);

    }


    /**
     * 비콘 Thread
     */
    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

//                List<ActivityManager.RunningTaskInfo> mActivityList = mActivityManager.getRunningTasks(1);
//
//                String motionActivityName = mActivityList.get(0).topActivity.toString();
//                Log.i("activityName", String.valueOf(motionActivityName));

                if (beacons.size() != 0) {
                    Log.i("Beacon Service : ", "beacon find");

                    ArrayList<Beacon> beaconList = new ArrayList<Beacon>(beacons);
                    Collections.sort(beaconList, new NoDescCompare());

                    int measuredRssi = beaconList.get(0).getRssi();


                    if (measuredRssi > mIgnore_Rssi) {
                        String beaconId1 = beaconList.get(0).getId1().toString();
                        String beaconId2 = beaconList.get(0).getId2().toString(); // major
                        int beaconMinor = beaconList.get(0).getId3().toInt(); // minor

                        setBeaconMinor(beaconMinor);


                        if (mIsAttendActivityComplete == true) {//AttendActivity가 실행됫을시
                            if (mFragmentDialog1Sign == true) {
                                callDialogFragment1();
                            }
                        }
                    }// 측정된 rssi가 크다면


                }// 비콘이 잡혓을때

                else {
                    Log.i("Beacon Service : ", "beacon not find");
                    setBeaconMinor(0);
                }//비콘 반응이 없을때
            }//end didRangeBeaconsInRegion
        });//setRangeNotifier
    }//onBeaconServiceConnect
//

    /**
     * 비콘 Thread start
     */
    public void startBeaconThread() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 비콘 Thread stop
     */
    public void stopBeaconThread() {
        handler.sendEmptyMessage(1);
    }

    /**
     * 비콘 Thread Handler
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int value = msg.what;
            switch (value) {
                case 0:
                    try {
                        mBeaconManager.startRangingBeaconsInRegion(mRegion);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case 1:
                    try {
                        mBeaconManager.stopRangingBeaconsInRegion(mRegion);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };

    private OnBeaconCallback mOnBeaconCallback;

    public void setOnBeaconCallback(ThisApplication.OnBeaconCallback mOnBeaconCallback) {
        this.mOnBeaconCallback = mOnBeaconCallback;
    }

    public interface OnBeaconCallback {
        void onBeacon(final DtoContent dtoContent);
    }

    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    private static class NoDescCompare implements Comparator<Beacon> {

        /**
         * 내림차순(DESC)
         */
        @Override
        public int compare(Beacon arg0, Beacon arg1) {
            // TODO Auto-generated method stub
            return arg0.getRssi() > arg1.getRssi() ? -1 : arg0.getRssi() < arg1.getRssi() ? 1 : 0;
        }

    }

    public void setIsMapInfoActivity(boolean mIsMapInfoActivity) {
        this.mIsMapInfoActivityComplete = mIsMapInfoActivity;
    }

    public void setFragmentDialog1Sign(boolean mFragmentDialog1Sign) {
        this.mFragmentDialog1Sign = mFragmentDialog1Sign;
    }

    public void setIsAttendActivityComplete(boolean mIsAttendActivityComplete) {
        this.mIsAttendActivityComplete = mIsAttendActivityComplete;
    }

    public void setMotionFragmentActivity(FragmentActivity mMotionActivity) {
        this.mMotionFragmentActivity = mMotionActivity;
    }

    public int getBeaconMinor() {
        return mBeaconMinor;
    }

    private void setBeaconMinor(int mBeaconMinor) {
        this.mBeaconMinor = mBeaconMinor;
    }

}
