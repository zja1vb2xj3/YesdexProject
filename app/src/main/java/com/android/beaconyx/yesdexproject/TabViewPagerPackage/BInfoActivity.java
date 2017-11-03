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
        mTabLayout.addTab(mTabLayout.newTab().setText("개막/환영식"));
        mTabLayout.addTab(mTabLayout.newTab().setText("버스킹 공연"));
        mTabLayout.addTab(mTabLayout.newTab().setText("중식 안내"));
        mTabLayout.addTab(mTabLayout.newTab().setText("경품 행사"));

        ArrayList<Fragment> fragments = getFragment();

        setViewPager(fragments);
    }

    private ArrayList<Fragment> getFragment(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        PageFragment pageFragment = new PageFragment(getApplicationContext());
        pageFragment.setmResId(R.mipmap.second_1);

        PageFragment pageFragment1 = new PageFragment(getApplicationContext());
        pageFragment1.setmResId(R.mipmap.second_2);

        PageFragment pageFragment2 = new PageFragment(getApplicationContext());
        pageFragment2.setmResId(R.mipmap.second_3);

        PageFragment pageFragment3 = new PageFragment(getApplicationContext());
        pageFragment3.setmResId(R.mipmap.second_4);

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
