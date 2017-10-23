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
//
public class AInfoActivity extends FragmentActivity {

    private TabLayout mTabLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ainfo);

        titleInit();
        aInfoInit();

    }


    private void titleInit(){
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.a_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void aInfoInit(){
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("인사말"));
        mTabLayout.addTab(mTabLayout.newTab().setText("행사개요"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ArrayList<Fragment> fragments = getFragment();

        setViewPager(fragments);
    }

    private ArrayList<Fragment> getFragment(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(PageFragment.newInstance("인사말"));

        fragments.add(PageFragment.newInstance("행사개요"));

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
                //ViewPager clear Page 가 변환 될때
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
