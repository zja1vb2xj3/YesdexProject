package com.android.beaconyx.yesdexproject.AccountPackage;

import android.util.Log;

import com.android.beaconyx.yesdexproject.Constant.TBAccountKoConstantPool;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by beaconyx on 2017-10-27.
 */

class AccountParseController {

    private String CLASSNAME = getClass().getSimpleName();

    //region OnRegisterUserCallback
    //유저 등록 콜백
    public interface OnRegistUserCallback {
        void onRegistUser(String objectId, String certifiValue);
    }

    private OnRegistUserCallback onRegistUserCallback;

    public void setOnRegisterUserCallback(OnRegistUserCallback onRegistUserCallback) {
        this.onRegistUserCallback = onRegistUserCallback;
    }
    //endregion

    /**
     * 유저 등록
     *
     * @param accountDtoModel
     */
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

                    final String objectId = parseObject.getObjectId();
                    final String certifiValue = parseObject.get(TBAccountKoConstantPool.ACT_CERTIFICATION).toString();

                    if (onRegistUserCallback != null) {
                        onRegistUserCallback.onRegistUser(objectId, certifiValue);
                    }
                } else {

                    if (onRegistUserCallback != null) {
                        onRegistUserCallback.onRegistUser(null, null);
                    }
                    Log.i(CLASSNAME, "registAccountUserData Error");
                }
            }
        });

    }

}
