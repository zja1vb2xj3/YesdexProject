package com.android.beaconyx.yesdexproject.ParseController;

import android.util.Log;

import com.android.beaconyx.yesdexproject.CheckPackage.AccountDtoModel;
import com.android.beaconyx.yesdexproject.Constant.TBAccountKoConstantPool;
import com.android.beaconyx.yesdexproject.Constant.TBUserKoConstantPool;
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

    private final String CLASSNAME = getClass().getSimpleName();

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
        final ParseObject parseObject = new ParseObject(TBAccountKoConstantPool.TB_Account_Ko);

        parseObject.put(TBAccountKoConstantPool.ACT_USER_ID, accountDtoModel.getACT_USER_ID());
        parseObject.put(TBAccountKoConstantPool.ACT_USER_OS, accountDtoModel.getACT_USER_OS());
        parseObject.put(TBAccountKoConstantPool.ACT_OS_VERSION, accountDtoModel.getACT_OS_VERSION());
        parseObject.put(TBAccountKoConstantPool.ACT_USER_PHONE_MODEL, accountDtoModel.getACT_USER_PHONE_MODEL());
        parseObject.put(TBAccountKoConstantPool.ACT_CERTIFICATION, accountDtoModel.getACT_CERTIFICATION());

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
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBAccountKoConstantPool.TB_Account_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(TBAccountKoConstantPool.ACT_USER_ID, uuid);

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
        void onCheckAuthentication(String userNumber, boolean resultSign);
    }

    public void setOnCheckAuthenticationCallback(OnCheckAuthenticationCallback onCheckAuthenticationCallback) {
        this.onCheckAuthenticationCallback = onCheckAuthenticationCallback;
    }
    //endregion


    //디비에 등록된 회원인지 체크
    public synchronized void checkRegistedUser(String userName, final String userNumber) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBUserKoConstantPool.TB_User_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(TBUserKoConstantPool.USR_NAME, userName);
        query.whereEqualTo(TBUserKoConstantPool.USR_NUMBER, userNumber);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() != 0) {// 있는거
                    if (onCheckAuthenticationCallback != null) {
                        onCheckAuthenticationCallback.onCheckAuthentication(userNumber, true);
                    }
                } else {
                    if (onCheckAuthenticationCallback != null) {
                        onCheckAuthenticationCallback.onCheckAuthentication(userNumber, false);
                    }
                }
            }
        });

    }

    //region OnUpdateCertificationCallback
    private OnUpdateCertificationCallback onUpdateCertificationCallback;

    public interface OnUpdateCertificationCallback {
        void onUpdate(String uuid, String userNumber, boolean resultSign);
    }

    public void setOnUpdateCertificationCallback(OnUpdateCertificationCallback onUpdateCertificationCallback) {
        this.onUpdateCertificationCallback = onUpdateCertificationCallback;
    }
    //endregion

    //TB_Account_Ko의 ACT_CERTIFICATION 을 true 로 바꾸기
    public synchronized void updateACT_CERTIFICATION(final String uuid, final String userNumber) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBAccountKoConstantPool.TB_Account_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(TBAccountKoConstantPool.ACT_USER_ID, uuid);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                boolean resultSign = false;

                if (e == null) {
                    object.put(TBAccountKoConstantPool.ACT_CERTIFICATION, "true");
                    object.saveInBackground().isCompleted();
                    resultSign = true;
                } else {
                    Log.i(CLASSNAME, "updateACT_CERTIFICATION error");
                }

                if (onUpdateCertificationCallback != null) {
                    onUpdateCertificationCallback.onUpdate(uuid, userNumber, resultSign);
                }
            }
        });

    }//endregion


    //region OnUpdateUSR_USER_IDCallback
    public interface OnUpdateUSR_USER_IDCallback {
        void onUpdate(boolean resultSign);
    }

    private OnUpdateUSR_USER_IDCallback onUpdateUSR_User_IdCallback;

    public void setOnUpdateUSR_User_IdCallback(OnUpdateUSR_USER_IDCallback onUpdateUSR_User_IdCallback) {
        this.onUpdateUSR_User_IdCallback = onUpdateUSR_User_IdCallback;
    }

    //endregion

    //TB_USER_KO의 USR_USER_ID에 UUID를 추가
    public synchronized void updateUSR_USER_ID(final String userNumber, final String uuid) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBUserKoConstantPool.TB_User_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(TBUserKoConstantPool.USR_NUMBER, userNumber);//TB_User_Ko USR_NUMBER 검색

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                boolean resultSign = false;

                if (e == null) {
                    //USR_USER_ID update
                    object.put(TBUserKoConstantPool.USR_USER_ID, uuid);
                    object.saveInBackground().isCompleted();
                    resultSign = true;
                } else {
                    Log.i(CLASSNAME, "updateACT_CERTIFICATION error");
                }

                if (onUpdateUSR_User_IdCallback != null) {
                    onUpdateUSR_User_IdCallback.onUpdate(resultSign);
                }
            }
        });
    }


    //region OnSearchUSR_USER_IDCallback

    public interface OnSearch_USR_USER_ID_Callback {
        void onSearch(String userNumber);
    }

    private OnSearch_USR_USER_ID_Callback onSearchUsrUserIdCallback;

    public void setOnSearchUsrUserIdCallback(OnSearch_USR_USER_ID_Callback onSearchUsrUserIdCallback) {
        this.onSearchUsrUserIdCallback = onSearchUsrUserIdCallback;
    }

    //TB_User_Ko의 uuid 검색
    public synchronized void searchUSR_USER_ID(final String uuid) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBUserKoConstantPool.TB_User_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.whereEqualTo(TBUserKoConstantPool.USR_USER_ID, uuid);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (object != null) {// 검색 실패
                    Object o = object.get(TBUserKoConstantPool.USR_NUMBER);

                    String userNumber = o.toString();

                    if (onSearchUsrUserIdCallback != null) {
                        onSearchUsrUserIdCallback.onSearch(userNumber);
                    }
                }

                else {
                    if (onSearchUsrUserIdCallback != null) {
                        onSearchUsrUserIdCallback.onSearch(null);
                    }
                    Log.i(CLASSNAME, "searchUSR_USER_ID error");
                }

//                if (e == null) {
//                    //USR_USER_ID update
//                    object.put(TBUserKoConstantPool.USR_USER_ID, uuid);
//                    object.saveInBackground().isCompleted();
//                    resultSign = true;
//                } else {
//                    Log.i(CLASSNAME, "updateACT_CERTIFICATION error");
//                }
//
//                if (onUpdateUSR_User_IdCallback != null) {
//                    onUpdateUSR_User_IdCallback.onUpdate(resultSign);
//                }
            }
        });
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (objects.size() != 0) {// 있는거
//                    if (onSearchUSR_user_idCallback != null) {
//
//                        onSearchUSR_user_idCallback.onSearch(true);
//                    }
//                } else {//없는거
//                    if (onSearchUSR_user_idCallback != null) {
//                        onSearchUSR_user_idCallback.onSearch(false);
//                    }
//                }
//            }
//        });
    }

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



