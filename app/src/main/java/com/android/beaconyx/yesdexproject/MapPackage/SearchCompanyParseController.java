package com.android.beaconyx.yesdexproject.MapPackage;

import com.android.beaconyx.yesdexproject.Constant.TBCompanyKoConstantPool;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by beaconyx on 2017-11-02.
 */

public class SearchCompanyParseController {

    interface OnFindCompanyContentsCallBack {
        void onFind(FindBeaconContentsModel model);
    }

    OnFindCompanyContentsCallBack onFindCompanyContentsCallBack;

    public void setOnFindCompanyContentsCallBack(OnFindCompanyContentsCallBack onFindCompanyContentsCallBack) {
        this.onFindCompanyContentsCallBack = onFindCompanyContentsCallBack;
    }

    synchronized void findCompanyContents(String beaconMinor) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TBCompanyKoConstantPool.TB_Company_Ko);

        query.whereEqualTo(TBCompanyKoConstantPool.CPY_BEACON_MINOR, beaconMinor);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e == null) {
                    FindBeaconContentsModel findBeaconContentsModel = new FindBeaconContentsModel();

                    if (!object.getString(TBCompanyKoConstantPool.CPY_TITLE).equalsIgnoreCase("undefined") || !object.getString(TBCompanyKoConstantPool.CPY_TITLE).equals(null))
                        findBeaconContentsModel.setCpyTitle(object.getString(TBCompanyKoConstantPool.CPY_TITLE));

                    if (!object.getString(TBCompanyKoConstantPool.CPY_TOP_IMG_1).equalsIgnoreCase("undefined") || !object.getString(TBCompanyKoConstantPool.CPY_TOP_IMG_1).equals(null))
                        findBeaconContentsModel.setCpyTopImg(object.getString(TBCompanyKoConstantPool.CPY_TOP_IMG_1));

                    if (!object.getString(TBCompanyKoConstantPool.CPY_ADDRESS).equalsIgnoreCase("undefined") || !object.getString(TBCompanyKoConstantPool.CPY_ADDRESS).equals(null))
                        findBeaconContentsModel.setCpyAddress(object.getString(TBCompanyKoConstantPool.CPY_ADDRESS));

                    if (!object.getString(TBCompanyKoConstantPool.SPY_PRODUCT_EXP).equalsIgnoreCase("undefined") || !object.getString(TBCompanyKoConstantPool.SPY_PRODUCT_EXP).equals(null))
                        findBeaconContentsModel.setSpyProductExp(object.getString(TBCompanyKoConstantPool.SPY_PRODUCT_EXP));

                    if (!object.getString(TBCompanyKoConstantPool.CPY_EXP).equalsIgnoreCase("undefined") || !object.getString(TBCompanyKoConstantPool.CPY_EXP).equals(null))
                        findBeaconContentsModel.setCpyExp(object.getString(TBCompanyKoConstantPool.CPY_EXP));

                    onFindCompanyContentsCallBack.onFind(findBeaconContentsModel);
                }

                else{
                    onFindCompanyContentsCallBack.onFind(null);
                }
            }
        });

    }
}
