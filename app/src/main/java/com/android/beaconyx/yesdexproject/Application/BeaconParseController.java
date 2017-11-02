package com.android.beaconyx.yesdexproject.Application;

import com.android.beaconyx.yesdexproject.Constant.TBCompanyKoConstantPool;
import com.android.beaconyx.yesdexproject.MapPackage.FindBeaconContentsModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beaconyx on 2017-11-01.
 */

public class BeaconParseController {
    private String CLASSNAME = getClass().getSimpleName();

    public interface OnFindBeaconContentsListCallBack {
        void onFindContentsList(ArrayList<FindBeaconContentsModel> findModels, boolean resultSign);
    }

    private OnFindBeaconContentsListCallBack onFindBeaconContentsListCallBack;

    public void setOnFindBeaconContentsCallBack(OnFindBeaconContentsListCallBack onFindBeaconContentsListCallBack) {
        this.onFindBeaconContentsListCallBack = onFindBeaconContentsListCallBack;
    }

    synchronized void findBeaconContentsList(String beaconid) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TBCompanyKoConstantPool.TB_Company_Ko);

        query.whereContains(TBCompanyKoConstantPool.CPY_BEACON_ID, beaconid);

        boolean isCache = query.hasCachedResult();
        if (isCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                //업체 이미지 등등...
                if (e == null) {

                    ArrayList<FindBeaconContentsModel> findBeaconContentsModels = new ArrayList<FindBeaconContentsModel>();

                    for (ParseObject parseObject : objects) {
                        FindBeaconContentsModel findBeaconContentModel = new FindBeaconContentsModel();

                        if (parseObject.getString(TBCompanyKoConstantPool.CPY_TITLE) != null)
                            findBeaconContentModel.setCpyTitle(parseObject.getString(TBCompanyKoConstantPool.CPY_TITLE));

                        if (parseObject.getString(TBCompanyKoConstantPool.CPY_ADDRESS) != null)
                            findBeaconContentModel.setCpyAddress(parseObject.getString(TBCompanyKoConstantPool.CPY_ADDRESS));

                        if (parseObject.getString(TBCompanyKoConstantPool.CPY_HOMEPAGE) != null)
                            findBeaconContentModel.setCpyHomePage(parseObject.getString(TBCompanyKoConstantPool.CPY_HOMEPAGE));

                        if (parseObject.getString(TBCompanyKoConstantPool.SPY_PRODUCT_EXP) != null)
                            findBeaconContentModel.setSpyProductExp(parseObject.getString(TBCompanyKoConstantPool.SPY_PRODUCT_EXP));

                        if (parseObject.getString(TBCompanyKoConstantPool.CPY_EXP) != null)
                            findBeaconContentModel.setCpyExp(parseObject.getString(TBCompanyKoConstantPool.CPY_EXP));

                        if (parseObject.getString(TBCompanyKoConstantPool.CPY_BEACON_MINOR) != null) {
                            findBeaconContentModel.setCpyBeaconMinor(parseObject.getString(TBCompanyKoConstantPool.CPY_BEACON_MINOR));
                        }

                        if (parseObject.getString(TBCompanyKoConstantPool.CPY_TOP_IMG_1) != null) {
                            findBeaconContentModel.setCpyTopImg(parseObject.getString(TBCompanyKoConstantPool.CPY_TOP_IMG_1));
                        }

                        if (parseObject.getString(TBCompanyKoConstantPool.CPY_THUMBNAIL_IMG) != null) {
                            findBeaconContentModel.setCpyThumbnailImg(parseObject.getString(TBCompanyKoConstantPool.CPY_THUMBNAIL_IMG));
                        }

                        findBeaconContentsModels.add(findBeaconContentModel);

                    }

                    if (onFindBeaconContentsListCallBack != null) {
                        onFindBeaconContentsListCallBack.onFindContentsList(findBeaconContentsModels, true);
                    }
                } else {
                    if (onFindBeaconContentsListCallBack != null) {
                        onFindBeaconContentsListCallBack.onFindContentsList(null, false);
                    }
                }
            }
        });
    }


}
