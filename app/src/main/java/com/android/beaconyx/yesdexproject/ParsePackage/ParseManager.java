package com.android.beaconyx.yesdexproject.ParsePackage;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beaconyx on 2017-10-25.
 */

public class ParseManager {
    private OnParseCallback mCallback;

    public interface OnParseCallback {
        void onParse(String data, boolean isSucess);
    }


    public void setOnParseCallback(OnParseCallback callback) {
        mCallback = callback;
    }

    public synchronized void load() {

    }
//ArrayList에 담아서 return 시켜야함
    public synchronized void loadUserData() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TB_USER_Ko");
        query.orderByAscending("USR_IDX");


        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                if (e == null) {
                    //for (int i = 0; i < parseObjects.size(); i++) {
//                    for(int i=0; i<parseObjects.size(); i++){
//                        parseObjects.get(i).getString("USR_NAME");
//                    }
                    for (int i = 0; i < parseObjects.size(); i++) {
                        ParseObject parseObject = parseObjects.get(i);
                        ArrayList<DtoUserModel> dtoUserModels = new ArrayList<DtoUserModel>();
                        String userName = null;
                        if (parseObject.getString("stamp_major") != null) {
                            userName = parseObject.getString("stamp_major");
                            dtoUserModels.get(i).setUserName(userName);
                        }

                        String userNumber = null;
                        if (parseObject.getString("stamp_minor") != null) {
                            userNumber = parseObject.getString("stamp_minor");
                            dtoUserModels.get(i).setUserNumber(userNumber);
                        }
                        if (parseObject.getInt("stamp_position") != 0) {
                            int idx = parseObject.getInt("stamp_position");
                            dtoUserModels.get(i).setIdx(idx);
                        }

                        if (mCallback != null) {
                            mCallback.onParse("\"major : \" + dto.getStampBeaconMajor() + \"\\n\" + \"minor : \" + dto.getmStampBeaconMinor() + \"\\n\" + \"position : \" + dto.getStampBeaconPosition()", true);
                        }
                        //mTestTextView.setText("major : " + dto.getStampBeaconMajor() + "\n" + "minor : " + dto.getmStampBeaconMinor() + "\n" + "position : " + dto.getStampBeaconPosition());
                    }
                }
            }
        });

    }//end


}
