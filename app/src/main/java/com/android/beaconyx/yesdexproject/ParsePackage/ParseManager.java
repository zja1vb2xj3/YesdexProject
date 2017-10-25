package com.android.beaconyx.yesdexproject.ParsePackage;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by beaconyx on 2017-10-25.
 */

public class ParseManager {
    private OnCheckAuthenticationCallBack onCheckAuthenticationCallBack;

    public interface OnCheckAuthenticationCallBack {
        void onCheck(boolean resultSign);
    }

    public void setOnCheckAuthenticationCallBack(OnCheckAuthenticationCallBack onCheckAuthenticationCallBack) {
        this.onCheckAuthenticationCallBack = onCheckAuthenticationCallBack;
    }

    public synchronized void load() {

    }

    public synchronized void checkAuthentication(String userName, String userNumber) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("TB_User_Ko");

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
                    if (onCheckAuthenticationCallBack != null) {

                        onCheckAuthenticationCallBack.onCheck(true);
                    }
                } else {
                    if (onCheckAuthenticationCallBack != null) {
                        onCheckAuthenticationCallBack.onCheck(false);
                    }
                }
            }
        });

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
//                    ArrayList<DtoUserModel> dtoUserModels = new ArrayList<>();
//
//                    for (int i = 0; i < parseObjects.size(); i++) {
//                        ParseObject parseObject = parseObjects.get(i);
//                        DtoUserModel dtoUserModel = new DtoUserModel();
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



