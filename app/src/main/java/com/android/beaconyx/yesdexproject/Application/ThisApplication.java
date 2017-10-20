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
import android.util.Log;
import android.view.Display;

import com.android.beaconyx.yesdexproject.Fragment.AttendDialogFragment1;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;

import java.util.Collection;
import java.util.List;

/**
 * Created by user on 2017-10-15.
 */

public class ThisApplication extends Application implements BeaconConsumer, BootstrapNotifier {
    private BeaconManager mBeaconManager;
    private Region mRegion;
    private AttendDialogFragment1 mDialogFragment1 = AttendDialogFragment1.newInstance();

    private final String CLASSNAME = "ThisApplication";

    private Point mDisplaySize = new Point();

    private boolean mIsAttendActivityComplete = false;
    private boolean mFragmentDialog1Sign = true;

    private ActivityManager mActivityManager;
    private FragmentActivity mMotionFragmentActivity = null;

    public ThisApplication() {

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

    @Override
    public void onCreate() {
        super.onCreate();
        beaconInit();
        Log.i(CLASSNAME, "onCreate");
        Log.i("isAttendActivity", String.valueOf(mIsAttendActivityComplete));
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        startBeaconThread();
    }

    private void callDialogFragment1() {
        if (mMotionFragmentActivity != null) {
            mMotionFragmentActivity.getFragmentManager().beginTransaction().remove(mDialogFragment1).commit();
            mDialogFragment1.show(mMotionFragmentActivity.getFragmentManager(), "fragment1");
        }


        mDialogFragment1.setOnDialogFragment1CancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
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

            Display display = activity.getWindowManager().getDefaultDisplay();

            display.getSize(mDisplaySize);
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
        mBeaconManager.setBackgroundBetweenScanPeriod(4000);
        mBeaconManager.setForegroundBetweenScanPeriod(4000);
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

                List<ActivityManager.RunningTaskInfo> mActivityList = mActivityManager.getRunningTasks(1);

                String motionActivityName = mActivityList.get(0).topActivity.toString();
                Log.i("activityName", String.valueOf(motionActivityName));

                if (mIsAttendActivityComplete == true) {//AttendActivity가 실행됫을시
                    if (mFragmentDialog1Sign == true) {
                        callDialogFragment1();
                    }
                }

//                if (beacons.size() != 0) {
//
//                    Log.i("Beacon Service : ", "find beacon");
//
//                    int beaconSize = beacons.size();
//                    Log.i("BeaconSize", String.valueOf(beaconSize));
//
//                    ArrayList<Beacon> beaconList = new ArrayList<Beacon>(beacons);
//
//                    int rssi = beaconList.get(0).getRssi();
//
//                    Log.i("Rssi", String.valueOf(rssi));
//
//                    Log.i("AttendActivityState", String.valueOf(mIsAttendActivityComplete));
//
//                    Log.i("FragmentDialog1Sign", String.valueOf(mFragmentDialog1Sign));


//                }//end if beacons size != 0

//                else
//                    Log.i("Beacon Service : ", "beacon not find");

            }//end didRangeBeaconsInRegion
        });//setRangeNotifier
    }//onBeaconServiceConnect

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

    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
