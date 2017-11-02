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
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.beaconyx.yesdexproject.AttendPackage.AttendDialogFragment1;
import com.parse.Parse;
import com.parse.ParseInstallation;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 2017-10-15.
 */

public class ThisApplication extends Application implements BeaconConsumer, BootstrapNotifier {
    private BeaconManager mBeaconManager;
    private Region mRegion;

    private boolean mIsProcessComplete = true;
    private boolean foregroundcheck;

    public final int mIgnore_Rssi = -90;

    private AttendDialogFragment1 mDialogFragment1 = AttendDialogFragment1.newInstance();

    private Point mDisplaySize = new Point();

    private final String CLASSNAME = "ThisApplication";

    private static String mTempBeaconID = null;
    private static String mTempBeaconID2 = null;
    public static HashMap<String, BeaconContentsModel> beaconContentsModelHashMap = new HashMap<>();//static으로 안하면 변할수가 있음 중복막기+ rssi값 보정

    @Override
    public void onCreate() {
        super.onCreate();
        parseInit();
        beaconInit();
        Log.i(CLASSNAME, "onCreate");

    }


    /**
     * Parse 서버 초기 설정
     */
    private void parseInit() {
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("YesdexaAZx4r86eeoyIwwGfdfOLeT2CnQKFcQ1")
                .clientKey("YesdexbeaconyxSwvy38GBFH6i1MZ2JGxfYkt2j4gaROGxy")
                .server("http://www.beaconyx.co.kr:1337/parse")   // '/' important after 'parse'
                .build());
        ParseInstallation.getCurrentInstallation().saveInBackground();
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
        mBeaconManager = BeaconManager.getInstanceForApplication(this);

        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        mRegion = new Region("myRangingUniqueId", Identifier.parse("a0fabefc-b1f5-4836-8328-7c5412fff9c4"), null, null);
        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.setAndroidLScanningDisabled(true);
        mBeaconManager.setBackgroundBetweenScanPeriod(2500);
        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.bind(this);

    }

    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.i(CLASSNAME, "Beacon Size : " + beacons.size());

                if (mIsProcessComplete) {
                    mIsProcessComplete = false;

                    if (beacons.size() == 0) {
                        mIsProcessComplete = true;
                        return;
                    }

                    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(1);
                    String runningActivityName = taskInfos.get(0).topActivity.getClassName();

                    List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

                    if (appProcesses == null) {
                        mIsProcessComplete = true;
                        return;
                    }

                    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                        if (appProcess.processName.equalsIgnoreCase("com.android.beaconyx.yesdexproject")) {
                            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                foregroundcheck = true;
                            } else {
                                foregroundcheck = false;
                            }
                        }
                    }

                    ArrayList<Beacon> tempBeaconList = new ArrayList<>(beacons);
                    Collections.sort(tempBeaconList, new NoDescCompare());
                    int beaconRssi = tempBeaconList.get(0).getRssi();

                    String beaconMajor = tempBeaconList.get(0).getId2().toString();
                    String beaconMinor = tempBeaconList.get(0).getId3().toString();

                    if (beaconMajor.equalsIgnoreCase("52")) {
                        callDialogFragment1();
                    } else if (beaconMajor.equalsIgnoreCase("51")) {

                        if (beaconRssi > mIgnore_Rssi) {
                            String beaconId = "m" + beaconMajor + "_" + beaconMinor;

                            if (mTempBeaconID == null) {
                                mTempBeaconID = beaconId;
                                BeaconContentsModel beaconContentsModel = beaconContentsModelHashMap.get(beaconId);

                                if (beaconContentsModel != null) {
                                    if (beaconRssi > mIgnore_Rssi /*+ Integer.parseInt(beaconContentsModel.getBeaconRssi())*/) {
                                        if (runningActivityName.contains("com.android.beaconyx.yesdexproject")) {
                                            Log.i(CLASSNAME, "delivery");
                                            mTempBeaconID2 = beaconId;


                                            deliveryContents(beaconContentsModel);
                                        } else {
                                            mTempBeaconID2 = beaconId;
                                        }
                                    } else {
                                        //mTempBeaconID = mTempBeaconID2;
                                    }
                                } else {
                                    mTempBeaconID = mTempBeaconID2;
                                }

                            } else {
                                if (!mTempBeaconID.equalsIgnoreCase(beaconId)) {
                                    mTempBeaconID = beaconId;

                                    BeaconContentsModel beaconContentsModel = beaconContentsModelHashMap.get(beaconId);
                                    if (beaconContentsModel != null) {
                                        if (beaconRssi > mIgnore_Rssi /*+ Integer.parseInt(beaconContentsModel.getBeaconRssi())*/) {
                                            mTempBeaconID2 = beaconId;
                                            beaconContentsModel.setBeaconID(mTempBeaconID);
                                            if (runningActivityName.contains("com.android.beaconyx.yesdexproject")) {
                                                Log.i(CLASSNAME, "delivery");
                                                deliveryContents(beaconContentsModel);
                                            } else {
                                                mTempBeaconID2 = beaconId;

                                            }
                                        } else {
                                            // mTempBeaconID = mTempBeaconID2;
                                        }
                                    } else {
                                        mTempBeaconID2 = beaconId;
                                    }
                                } else {
                                    mIsProcessComplete = true;
                                }
                            }

                            mIsProcessComplete = true;
                        } else {
                            mIsProcessComplete = true;
                        }
                    }
                    mIsProcessComplete = true;
                }

            }
        });
    }


    //region 엑티비티에 전달될 콜백
    public interface OnBeaconEventCallBack {
        void onBeaconEvent(BeaconContentsModel beaconContentsModel);
    }

    private OnBeaconEventCallBack onBeaconEventCallBack;

    public void setOnBeaconEventCallBack(OnBeaconEventCallBack onBeaconEventCallBack) {
        this.onBeaconEventCallBack = onBeaconEventCallBack;
    }
    //endregion

    private void deliveryContents(BeaconContentsModel model) {
        Message message = new Message();

        message.obj = model;

        deliveryEventHandler.sendMessage(message);//deliveryContents 메소드를 호출하면 deliveryEventHandler가 작동됨
    }


    Handler deliveryEventHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            BeaconContentsModel beaconContentsModel = (BeaconContentsModel) msg.obj;

            if (beaconContentsModel != null) {
                onBeaconEventCallBack.onBeaconEvent(beaconContentsModel);
            } else {
                onBeaconEventCallBack.onBeaconEvent(null);
            }

            mIsProcessComplete = true;
        }
    };

    /**
     * 비콘 Thread start
     */
    public void startBeaconThread() {
        mIsProcessComplete = true;
        beaconThreadHandler.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 비콘 Thread stop
     */
    public void stopBeaconThread() {
        mIsProcessComplete = false;
        beaconThreadHandler.sendEmptyMessage(1);
    }


    //region 비콘 Thread Handler
    Handler beaconThreadHandler = new Handler() {
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
//endregion


    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    /**
     * 디바이스 UUID를 생성 후 반환
     *
     * @return deviceUUID
     */
    public String getDeviceUUID() {
        UUID deviceUUID;

        final String androidUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            if (!androidUID.equals("")) {
                deviceUUID = UUID.nameUUIDFromBytes(androidUID.getBytes("utf8"));
            } else {
                final String anotherUID = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                if (anotherUID != null) {
                    deviceUUID = UUID.nameUUIDFromBytes(anotherUID.getBytes("utf8"));
                } else {
                    deviceUUID = UUID.randomUUID();
                }
            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return deviceUUID.toString();
    }

    /**
     * 정렬
     */
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

    private boolean fragmentDialogSign = false;

    public void setFragmentDialogSign(boolean fragmentDialogSign) {
        this.fragmentDialogSign = fragmentDialogSign;
    }

    private FragmentActivity fragmentActivity;

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    //region FragmentDialog1 부르기
    private void callDialogFragment1() {
        if (fragmentActivity != null && fragmentDialogSign == true) {
            fragmentActivity.getFragmentManager().beginTransaction().remove(mDialogFragment1).commit();
            mDialogFragment1.show(fragmentActivity.getFragmentManager(), "fragment1");

            fragmentDialogSign = false;
        }
        else{
            Log.i(CLASSNAME, "fragmentActivity is null");
        }

        mDialogFragment1.setOnDialogFragment1CancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });
    }

//endregion

}
