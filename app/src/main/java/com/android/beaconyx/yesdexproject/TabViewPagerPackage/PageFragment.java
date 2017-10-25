package com.android.beaconyx.yesdexproject.TabViewPagerPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.beaconyx.yesdexproject.R;

/**
 * Created by beaconyx on 2017-10-09.
 */

public class PageFragment extends Fragment {


    private Button pageFragmentViewItem;
    private int mResId;
    private String contents;

    public static final PageFragment newInstance(String data){
        PageFragment pageFragment = new PageFragment(data);

        return pageFragment;
    }

    public PageFragment(int resId){
        mResId = resId;
    }

    public PageFragment(String data){
        contents = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_fragment, container, false);
        pageFragmentViewItem = (Button) view.findViewById(R.id.pagerimg);
        pageFragmentViewItem.setGravity(Gravity.CENTER);
        pageFragmentViewItem.setTextSize(30);
        pageFragmentViewItem.setText(contents);

//        try {
//            PhotoViewAttacher attacher = new PhotoViewAttacher(pageFragmentViewItem);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
