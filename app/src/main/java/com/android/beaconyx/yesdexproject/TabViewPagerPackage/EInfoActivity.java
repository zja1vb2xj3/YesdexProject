package com.android.beaconyx.yesdexproject.TabViewPagerPackage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.R;

import java.util.ArrayList;

public class EInfoActivity extends FragmentActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einfo);

        titleInit();
        taInfoInit();
    }

    private void titleInit(){
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.e_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void taInfoInit(){
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("교통 안내"));
        mTabLayout.addTab(mTabLayout.newTab().setText("숙박 안내"));
        mTabLayout.addTab(mTabLayout.newTab().setText("관광 안내"));
        mTabLayout.addTab(mTabLayout.newTab().setText("주변 안내"));

        ArrayList<Fragment> fragments = getFragment();

        setViewPager(fragments);
    }

    private ArrayList<Fragment> getFragment(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        PageFragment pageFragment = new PageFragment();
        pageFragment.setmResId(R.mipmap.fouth_1);

        PageFragment pageFragment1 = new PageFragment();
        pageFragment1.setmResId(R.mipmap.fouth_2);

        PageFragment pageFragment2 = new PageFragment();
        pageFragment2.setmResId(R.mipmap.fouth_3);

        PageFragment pageFragment3 = new PageFragment();
        pageFragment3.setmResId(R.mipmap.fouth_4);


        fragments.add(pageFragment);
        fragments.add(pageFragment1);
        fragments.add(pageFragment2);
        fragments.add(pageFragment3);

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
