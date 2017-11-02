package com.android.beaconyx.yesdexproject.MapPackage;

/**
 * Created by beaconyx on 2017-11-02.
 */

public class SearchCompanyContentsThread extends Thread {
    private SearchCompanyParseController searchCompanyParseController;
    private String beaconMinor;

    public SearchCompanyContentsThread(SearchCompanyParseController parseController, String beaconMinor){
        searchCompanyParseController = parseController;
        this.beaconMinor = beaconMinor;
    }

    @Override
    public void run() {
        super.run();
        searchCompanyParseController.findCompanyContents(beaconMinor);
    }
}
