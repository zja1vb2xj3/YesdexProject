package com.android.beaconyx.yesdexproject.TabViewPagerPackage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by beaconyx on 2017-10-09.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;


    public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /*
     * tab 개수대로 동적으로 늘려야함
     */
    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }


}
