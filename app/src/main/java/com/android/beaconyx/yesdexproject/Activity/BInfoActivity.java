package com.android.beaconyx.yesdexproject.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Adapter.PagerAdapter;
import com.android.beaconyx.yesdexproject.CustomView.CustomViewPager;
import com.android.beaconyx.yesdexproject.Fragment.PageFragment;
import com.android.beaconyx.yesdexproject.R;

import java.util.ArrayList;

public class BInfoActivity extends FragmentActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binfo);

        titleInit();
        bInfoInit();
    }
    private void titleInit(){
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.b_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void bInfoInit(){
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("개막식 / 환영리셉션"));
        mTabLayout.addTab(mTabLayout.newTab().setText("버스킹 공연"));
        mTabLayout.addTab(mTabLayout.newTab().setText("중식 안내"));
        mTabLayout.addTab(mTabLayout.newTab().setText("경품 행사 안내"));

        ArrayList<Fragment> fragments = getFragment();

        setViewPager(fragments);
    }

    private ArrayList<Fragment> getFragment(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(PageFragment.newInstance("개막식 / 환영리셉션"));
        fragments.add(PageFragment.newInstance("버스킹 공연"));
        fragments.add(PageFragment.newInstance("중식 안내"));
        fragments.add(PageFragment.newInstance("경품 행사 안내"));

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
