package com.android.beaconyx.yesdexproject.Manager;

import com.android.beaconyx.yesdexproject.Dto.DtoUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
        loadStampBeaconData();
    }

    private void loadStampBeaconData() {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("TB_Test_Stamp_Beacon");
        query.orderByAscending("stamp_position");


        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.orderByAscending("idx");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                if (e == null) {
                    //for (int i = 0; i < parseObjects.size(); i++) {
                    for(int i=0; i<parseObjects.size(); i++){
                        parseObjects.get(i).getString("stamp_major");
                    }
                    for (int i = 0; i < 1; i++) {
                        ParseObject parseObject = parseObjects.get(i);
                        DtoUser dto = new DtoUser();
                        String userName = null;
                        if (parseObject.getString("stamp_major") != null) {
                            userName = parseObject.getString("stamp_major");
                            dto.setUserName(userName);
                        }

                        String userNumber = null;
                        if (parseObject.getString("stamp_minor") != null) {
                            userNumber = parseObject.getString("stamp_minor");
                            dto.setUserNumber(userNumber);
                        }
                        if (parseObject.getInt("stamp_position") != 0) {
                            int idx = parseObject.getInt("stamp_position");
                            dto.setIdx(idx);
                        }

                        if (mCallback != null) {
                            mCallback.onParse("\"major : \" + dto.getStampBeaconMajor() + \"\\n\" + \"minor : \" + dto.getmStampBeaconMinor() + \"\\n\" + \"position : \" + dto.getStampBeaconPosition()", true);
                        }
                        //mTestTextView.setText("major : " + dto.getStampBeaconMajor() + "\n" + "minor : " + dto.getmStampBeaconMinor() + "\n" + "position : " + dto.getStampBeaconPosition());
                    }
                }
            }
        });
        ;
    }//end


}
