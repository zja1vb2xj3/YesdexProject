package com.android.beaconyx.yesdexproject.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.android.beaconyx.yesdexproject.Adapter.PagerAdapter;
import com.android.beaconyx.yesdexproject.CustomView.CustomViewPager;
import com.android.beaconyx.yesdexproject.Fragment.PageFragment;
import com.android.beaconyx.yesdexproject.R;

import java.util.ArrayList;

public class CInfoActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinfo);

        cInfoInit();
    }

    private void cInfoInit(){
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("학술프로그램 소개"));
        mTabLayout.addTab(mTabLayout.newTab().setText("강연장 안내"));
        mTabLayout.addTab(mTabLayout.newTab().setText("강연자 소개"));

        ArrayList<Fragment> fragments = getFragment();

        setViewPager(fragments);
    }

    private ArrayList<Fragment> getFragment(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(PageFragment.newInstance(R.drawable.aimage));
        fragments.add(PageFragment.newInstance(R.drawable.bimage));
        fragments.add(PageFragment.newInstance(R.drawable.cimage));

        return fragments;
    }


    private void setViewPager(ArrayList<Fragment> fragments){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);

        final CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
