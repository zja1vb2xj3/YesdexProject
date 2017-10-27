package com.android.beaconyx.yesdexproject.AccountPackage;

/**
 * Created by beaconyx on 2017-10-27.
 */

class RegistAccountThread extends Thread {
    private AccountParseController mController;
    private AccountDtoModel mAccountDtoModel;

    RegistAccountThread(AccountParseController controller, AccountDtoModel model){
        mController = controller;
        mAccountDtoModel = model;
    }

    @Override
    public void run() {
        super.run();
        mController.registAccountUserData(mAccountDtoModel);
    }
}
