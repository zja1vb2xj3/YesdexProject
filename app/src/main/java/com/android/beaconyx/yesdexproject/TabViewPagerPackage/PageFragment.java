package com.android.beaconyx.yesdexproject.TabViewPagerPackage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.beaconyx.yesdexproject.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by beaconyx on 2017-10-09.
 */

public class PageFragment extends Fragment {

    private int mResId;
    private Context context;

    public PageFragment(Context context) {
        this.context = context;
    }

    public void setmResId(int mResId) {
        this.mResId = mResId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment, container, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);

        PhotoView pageImageView = view.findViewById(R.id.pagerimg);
        pageImageView.setImageResource(mResId);
        pageImageView.setScaleType(ImageView.ScaleType.FIT_START);
        pageImageView.setMinimumScale(1f);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
