package com.android.beaconyx.yesdexproject.Load;

import com.android.beaconyx.yesdexproject.Constant.TBAccountKoConstantPool;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by beaconyx on 2017-10-27.
 */

class LoadParseController extends Thread {
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
