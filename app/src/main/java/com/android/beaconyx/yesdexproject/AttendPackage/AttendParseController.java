package com.android.beaconyx.yesdexproject.AttendPackage;

import android.util.Log;

import com.android.beaconyx.yesdexproject.Constant.TBAccountKoConstantPool;
import com.android.beaconyx.yesdexproject.Constant.TBUserKoConstantPool;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by beaconyx on 2017-10-25.
 */

class AttendParseController {

    private final String CLASSNAME = getClass().getSimpleName();

    //region OnSearchRegistedUserCallback
    public interface OnSearchRegistedUserCallback {
        void onCheck(boolean resultSign);
    }

    private OnSearchRegistedUserCallback onSearchRegistedUserCallback;

    public void setOnSearchRegistedUserCallback(OnSearchRegistedUserCallback onSearchRegistedUserCallback) {
        this.onSearchRegistedUserCallback = onSearchRegistedUserCallback;
    }

    //endregion

    //디비에 등록된 회원인지 체크 후 등록된 회원에게 uuid 부여
    synchronized void checkRegistUser(final String userName, final String userNumber, final String uuid) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBUserKoConstantPool.TB_User_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(TBUserKoConstantPool.USR_NAME, userName);
        query.whereEqualTo(TBUserKoConstantPool.USR_NUMBER, userNumber);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {// 검색 성공

                    Object o = object.get(TBUserKoConstantPool.USR_NUMBER);

                    String userNumber = o.toString();

                    /**
                     * TB_USER_Ko 에 해당 유저에 uuid 등록
                     */
                    object.put(TBUserKoConstantPool.USR_USER_ID, uuid);
                    object.saveInBackground().isCompleted();


                    if (onSearchRegistedUserCallback != null) {
                        onSearchRegistedUserCallback.onCheck(true);
                    }
                } else {//검색 실패
                    if (onSearchRegistedUserCallback != null) {
                        onSearchRegistedUserCallback.onCheck(false);
                    }
                }
            }
        });
    }


    //region OnUpdateCertificationCallback
    private OnUpdateCertificationCallback onUpdateCertificationCallback;

    public interface OnUpdateCertificationCallback {
        void onUpdate(String certifiValue);
    }

    public void setOnUpdateCertificationCallback(OnUpdateCertificationCallback onUpdateCertificationCallback) {
        this.onUpdateCertificationCallback = onUpdateCertificationCallback;
    }
    //endregion

    //TB_Account_Ko의 ACT_CERTIFICATION 을 true 로 바꾸기
    public synchronized void updateCertification(final String uuid) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBAccountKoConstantPool.TB_Account_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(TBAccountKoConstantPool.ACT_USER_ID, uuid);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (object != null) {

                    object.put(TBAccountKoConstantPool.ACT_CERTIFICATION, "true");

                    Object certifiValue = object.get(TBAccountKoConstantPool.ACT_CERTIFICATION);

                    object.saveInBackground().isCompleted();

                    if (onUpdateCertificationCallback != null) {
                        onUpdateCertificationCallback.onUpdate(certifiValue.toString());
                    }
                }

                else {

                    if (onUpdateCertificationCallback != null) {
                        onUpdateCertificationCallback.onUpdate("false");
                    }

                    Log.i(CLASSNAME, "updateCertification error");
                }
            }
        });
    }


}



