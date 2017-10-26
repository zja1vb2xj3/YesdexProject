package com.android.beaconyx.yesdexproject.ParseController;

import android.util.Log;

import com.android.beaconyx.yesdexproject.CheckPackage.AccountDtoModel;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by beaconyx on 2017-10-25.
 */

public class ParseManager {

    private String CLASSNAME = getClass().getSimpleName();
    /**
     * TB_Account_Ko Table set
     */
    private final String TB_Account_Ko = "TB_Account_Ko";
    private final String ACT_USER_ID = "ACT_USER_ID";
    private final String ACT_USER_OS = "ACT_USER_OS";
    private final String ACT_OS_VERSION = "ACT_OS_VERSION";
    private final String ACT_USER_PHONE_MODEL = "ACT_USER_PHONE_MODEL";
    private final String ACT_CERTIFICATION = "ACT_CERTIFICATION";

    /**
     * TB_User_Ko Table set
     */
    private final String TB_User_Ko = "TB_User_Ko";

    //region OnRegisterUserCallback
    //유저 등록 콜백
    public interface OnRegisterUserCallback {
        void onRegistUser();
    }

    private OnRegisterUserCallback onRegisterUserCallback;

    public void setOnRegisterUserCallback(OnRegisterUserCallback onRegisterUserCallback) {
        this.onRegisterUserCallback = onRegisterUserCallback;
    }
    //endregion

    //유저 등록
    public synchronized void registAccountUserData(final AccountDtoModel accountDtoModel) {
        final ParseObject parseObject = new ParseObject(TB_Account_Ko);

        parseObject.put(ACT_USER_ID, accountDtoModel.getACT_USER_ID());
        parseObject.put(ACT_USER_OS, accountDtoModel.getACT_USER_OS());
        parseObject.put(ACT_OS_VERSION, accountDtoModel.getACT_OS_VERSION());
        parseObject.put(ACT_USER_PHONE_MODEL, accountDtoModel.getACT_USER_PHONE_MODEL());
        parseObject.put(ACT_CERTIFICATION, accountDtoModel.getACT_CERTIFICATION());

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    if (onRegisterUserCallback != null) {
                        onRegisterUserCallback.onRegistUser();
                    }
                } else {
                    Log.i(CLASSNAME, "registAccountUserData Error");
                }
            }
        });
    }

    //region OnCheckRegisterdDeviceCallback
    //등록된 디바이스 인지 체크
    public interface OnCheckRegisterdDeviceCallback {
        void onCheckDevice(boolean resultSign);
    }

    private OnCheckRegisterdDeviceCallback onCheckRegisterdDeviceCallback;

    public void setOnCheckRegisterdDeviceCallback(OnCheckRegisterdDeviceCallback onCheckRegisterdDeviceCallback) {
        this.onCheckRegisterdDeviceCallback = onCheckRegisterdDeviceCallback;
    }
//endregion

    /**
     * 등록된 디바이스 인지 체크
     */
    public synchronized void checkRegisteredDevice(final String uuid) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TB_Account_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo("ACT_USER_ID", uuid);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() != 0) {// 있는거
                    if (onCheckRegisterdDeviceCallback != null) {

                        onCheckRegisterdDeviceCallback.onCheckDevice(true);
                    }
                } else {//없는거
                    if (onCheckRegisterdDeviceCallback != null) {
                        onCheckRegisterdDeviceCallback.onCheckDevice(false);
                    }
                }
            }
        });

    }


    //region OnCheckAuthenticationCallback
    private OnCheckAuthenticationCallback onCheckAuthenticationCallback;

    public interface OnCheckAuthenticationCallback {
        void onCheckAuthentication(boolean resultSign);
    }

    public void setOnCheckAuthenticationCallback(OnCheckAuthenticationCallback onCheckAuthenticationCallback) {
        this.onCheckAuthenticationCallback = onCheckAuthenticationCallback;
    }
    //endregion

    /**
     * (디비에 등록된 회원인지 체크)
     *
     * @param userName
     * @param userNumber
     */
    public synchronized void checkRegistedUser(String userName, String userNumber) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TB_User_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo("USR_NAME", userName);
        query.whereEqualTo("USR_NUMBER", userNumber);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() != 0) {// 있는거
                    if (onCheckAuthenticationCallback != null) {
                        onCheckAuthenticationCallback.onCheckAuthentication(true);
                    }
                } else {
                    if (onCheckAuthenticationCallback != null) {
                        onCheckAuthenticationCallback.onCheckAuthentication(false);
                    }
                }
            }
        });

    }

    //region OnUpdateCertificationCallback
    private OnUpdateCertificationCallback onUpdateCertificationCallback;

    public interface OnUpdateCertificationCallback {
        void onUpdate(boolean resultSign);
    }

    public void setOnUpdateCertificationCallback(OnUpdateCertificationCallback onUpdateCertificationCallback) {
        this.onUpdateCertificationCallback = onUpdateCertificationCallback;
    }

    //endregion

    //TB_Account_Ko의 ACT_CERTIFICATION 을 true 로 바꾸기
    public synchronized void updateACT_CERTIFICATION(String uuid) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TB_Account_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(ACT_USER_ID, uuid);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                boolean resultSign = false;

                if (e == null) {
                    object.put(ACT_CERTIFICATION, "true");
                    object.saveInBackground().isCompleted();
                    resultSign = true;
                }

                else{
                    Log.i(CLASSNAME, "updateACT_CERTIFICATION error");
                }

                if (onUpdateCertificationCallback != null) {
                    onUpdateCertificationCallback.onUpdate(resultSign);
                }
            }
        });

    }

    //TB_USER_KO의 USR_USER_ID에 UUID를 추가


//    public synchronized void loadUserData() {
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("TB_USER_Ko");
//
//        boolean isCache = query.hasCachedResult();
//
//        if (isCache == true) {
//            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
//        }
//
//        query.orderByAscending("USR_IDX");
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//
//            @Override
//            public void done(List<ParseObject> parseObjects, ParseException e) {
//
//                if (e == null) {
//
//                    ArrayList<> dtoUserModels = new ArrayList<>();
//
//                    for (int i = 0; i < parseObjects.size(); i++) {
//                        ParseObject parseObject = parseObjects.get(i);
//                         dtoUserModel = new ();
//
//                        String userName = null;
//                        if (parseObject.getString("USR_NAME") != null) {
//                            userName = parseObject.getString("USR_NAME");
//                            dtoUserModel.setUserName(userName);
//                        }
//
//                        String userNumber = null;
//                        if (parseObject.getString("USR_NUMBER") != null) {
//                            userNumber = parseObject.getString("USR_NUMBER");
//                            dtoUserModel.setUserNumber(userNumber);
//                        }
//                        if (parseObject.getInt("USR_IDX") != 0) {
//                            int idx = parseObject.getInt("USR_IDX");
//                            dtoUserModel.setIdx(idx);
//                        }
//
//                        dtoUserModels.add(dtoUserModel);
//                    }
//
//                    if (mAttendParseCallBack != null) {
//                        mAttendParseCallBack.onParse(dtoUserModels, true);
//                    }
//                } else {
//                    Log.i("Parse 에러임", e.toString());
//                }
//            }
//        });
//}
}



