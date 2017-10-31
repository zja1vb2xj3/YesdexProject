package com.android.beaconyx.yesdexproject.TabViewPagerPackage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by beaconyx on 2017-10-09.
 */

public class PageFragment extends Fragment {


    private SubsamplingScaleImageView pageImageView;
    private int mResId;


    public static final PageFragment newInstance(int resId) {
        PageFragment pageFragment = new PageFragment(resId);

        return pageFragment;
    }


    public PageFragment(int resId) {
        mResId = resId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_fragment, container, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
//        view.setForegroundGravity(Gravity.TOP);

        pageImageView = view.findViewById(R.id.pagerimg);
        pageImageView.setImage(ImageSource.resource(mResId));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
