package com.android.beaconyx.yesdexproject.Load;

import com.android.beaconyx.yesdexproject.Constant.TBAccountKoConstantPool;
import com.android.beaconyx.yesdexproject.Constant.TBBeaconContentsKoConstantPool;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by beaconyx on 2017-10-30.
 */

public class LoadParseController {

    //region OnBeaconContentsCallback
    public interface OnBeaconContentsCallback {
        void onParse(boolean resultSign);
    }

    OnBeaconContentsCallback onBeaconContentsCallback;

    public void setOnBeaconContentsCallback(OnBeaconContentsCallback onBeaconContentsCallback) {
        this.onBeaconContentsCallback = onBeaconContentsCallback;
    }
    //endregion

    //beaconContents parse 서버에서 받아옴
    public synchronized void loadBeaconContents() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TBBeaconContentsKoConstantPool.TB_Beacon_Contents_Ko);
        query.orderByAscending(TBBeaconContentsKoConstantPool.BCS_IDX);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {//에러가 null이라면

                    DtoListPool dtoListPool = DtoListPool.getInstance();

                    for (int i = 0; i < objects.size(); i++) {
                        BeaconContentsModel model = new BeaconContentsModel();

                        model.setIdx(objects.get(i).getInt(TBBeaconContentsKoConstantPool.BCS_IDX));
                        model.setBeaconID(objects.get(i).getString(TBBeaconContentsKoConstantPool.BCS_BEACON_ID));
                        model.setBeaconMajor(objects.get(i).getString(TBBeaconContentsKoConstantPool.BCS_BEACON_MAJOR));
                        model.setBeaconMinor(objects.get(i).getString(TBBeaconContentsKoConstantPool.BCS_BEACON_MINOR));
                        model.setMapPositionX(Integer.parseInt(objects.get(i).getString(TBBeaconContentsKoConstantPool.BCS_ISE_X_ANDROID)));
                        model.setMapPositionY(Integer.parseInt(objects.get(i).getString(TBBeaconContentsKoConstantPool.BCS_ISE_Y_ANDROID)));

                        dtoListPool.addBeaconContentsModelList(model);

                    }//end for
                    if (onBeaconContentsCallback != null) {
                        onBeaconContentsCallback.onParse(true);
                    }
                }//end if e == null

                else {
                    if (onBeaconContentsCallback != null) {
                        onBeaconContentsCallback.onParse(false);
                    }
                }
            }
        });

    }//end

    //region OnCheckRegisterdDeviceCallback
    public interface OnCheckRegistedDeviceCallback {
        void onCheckDeviceUUID(String objectId, String certifivalue);
    }

    private OnCheckRegistedDeviceCallback onCheckRegistedDeviceCallback;

    public void setOnCheckRegisterdDeviceCallback(OnCheckRegistedDeviceCallback onCheckRegistedDeviceCallback) {
        this.onCheckRegistedDeviceCallback = onCheckRegistedDeviceCallback;
    }
    //endregion

    /**
     * 등록된 디바이스 인지 체크
     */
    public synchronized void checkAccountKoRegistedUserId(final String uuid) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(TBAccountKoConstantPool.TB_Account_Ko);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.whereEqualTo(TBAccountKoConstantPool.ACT_USER_ID, uuid);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (object != null) {// 검색 성공

                    String objectId = object.getObjectId();
                    String certifiValue = object.get(TBAccountKoConstantPool.ACT_CERTIFICATION).toString();

                    if (onCheckRegistedDeviceCallback != null) {
                        onCheckRegistedDeviceCallback.onCheckDeviceUUID(objectId, certifiValue);
                    }

                } else {//검색 실패
                    if (onCheckRegistedDeviceCallback != null) {
                        onCheckRegistedDeviceCallback.onCheckDeviceUUID(null, null);
                    }
                }
            }
        });
    }

}
