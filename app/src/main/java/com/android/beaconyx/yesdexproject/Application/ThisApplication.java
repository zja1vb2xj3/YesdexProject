package com.android.beaconyx.yesdexproject.Application;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

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

/**
 * Created by user on 2017-10-15.
 */

public class ThisApplication extends Application implements BeaconConsumer, BootstrapNotifier {
    private static ThisApplication thisApplication;
    private BeaconManager mBeaconManager;
    private Region mRegion;

    private final String CLASSNAME = "ThisApplication";

    public static boolean isAttendActivityComplete = false;

    public static ThisApplication newInstance() {
        if (thisApplication == null) {
            thisApplication = new ThisApplication();
        }

        return thisApplication;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        beaconInit();
        Log.i(CLASSNAME, "onCreate");
        Log.i("isAttendActivity", String.valueOf(isAttendActivityComplete));


        startBeaconThread();
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
                if (beacons.size() != 0) {

                    if(isAttendActivityComplete == true){//AttendActivity가 실행됫을시
                        Log.i("Beacon Service : ", "find beacon");

                        int beaconSize = beacons.size();
                        Log.i("BeaconSize", String.valueOf(beaconSize));

                        ArrayList<Beacon> beaconList = new ArrayList<Beacon>(beacons);

                        int rssi = beaconList.get(0).getRssi();

                        Log.i("Rssi", String.valueOf(rssi));
                    }


//                    if (fragment1CallSign == false) {
//                        handler.sendEmptyMessageDelayed(1, 1000);
//                        callDialogFragment1();
//                        fragment1CallSign = true;
//                    }

                }//end if beacons size != 0

                else
                    Log.i("Beacon Service : ", "beacon not find");

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
