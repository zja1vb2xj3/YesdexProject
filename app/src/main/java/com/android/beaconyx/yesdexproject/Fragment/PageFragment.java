package com.android.beaconyx.yesdexproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.beaconyx.yesdexproject.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by beaconyx on 2017-10-09.
 */

public class PageFragment extends Fragment {


    private ImageView mPagerImage;
    private int mResId;

    public static final PageFragment newInstance(int resId){
        PageFragment pageFragment = new PageFragment(resId);

        return pageFragment;
    }

    public PageFragment(int resId){
        mResId = resId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pager_fragment, container, false);
        mPagerImage = (ImageView) view.findViewById(R.id.pagerimg);
        mPagerImage.setImageResource(mResId);

        try {
            PhotoViewAttacher attacher = new PhotoViewAttacher(mPagerImage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
