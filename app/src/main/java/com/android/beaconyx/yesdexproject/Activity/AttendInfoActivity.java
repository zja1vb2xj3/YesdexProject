package com.android.beaconyx.yesdexproject.Activity;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;

import com.android.beaconyx.yesdexproject.Adapter.HeaderListViewAdapter;
import com.android.beaconyx.yesdexproject.Fragment.AttendDialogFragment1;
import com.android.beaconyx.yesdexproject.Fragment.AttendDialogFragment2;
import com.android.beaconyx.yesdexproject.Model.HeaderListViewModel;
import com.android.beaconyx.yesdexproject.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AttendInfoActivity extends FragmentActivity {

    private StickyListHeadersListView mLectureInfoListView;
    private HeaderListViewAdapter mHeaderListViewAdapter;

    private AttendDialogFragment1 mDialogFragment1;
    private AttendDialogFragment2 mDialogFragment2;
    Point mSize = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_info);

        initView();


        mDialogFragment1 = AttendDialogFragment1.newInstance();
        mDialogFragment2 = AttendDialogFragment2.newInstance();

    }//end onCreate

    private void initView(){

        mLectureInfoListView = (StickyListHeadersListView) findViewById(R.id.lecture_info_list);
        mHeaderListViewAdapter = new HeaderListViewAdapter(getApplicationContext());

        HeaderListViewModel headerListViewModel1 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel2 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel3 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel4 = new HeaderListViewModel();
//
        headerListViewModel1.setmHeaderid(1);
        headerListViewModel1.setmHeader("11월 10일 (금)");
        headerListViewModel1.setmEducatorName("강연자 : 최점일 교수");
        headerListViewModel1.setmEducatorTime("강연 시간 : 14:00 ~ 15:00");
        headerListViewModel1.setmEducatorTitle("강연제목 : 모든 임상가를 위한...");

        headerListViewModel1.setDemoEduImage(R.drawable.demo_header_img1);
        headerListViewModel1.setDemoStateImage(R.drawable.demo_state_img1);
//
        headerListViewModel2.setmHeaderid(1);
        headerListViewModel2.setmHeader("11월 10일 (금)");
        headerListViewModel2.setmEducatorName("강연자 : 손우성 교수");
        headerListViewModel2.setmEducatorTime("강연 시간 : 15:00 ~ 16:00");
        headerListViewModel2.setmEducatorTitle("강연제목 : 치과의사의 직업전문성...");

        headerListViewModel2.setDemoEduImage(R.drawable.demo_header_img2);
        headerListViewModel2.setDemoStateImage(R.drawable.demo_state_img2);
//
        headerListViewModel3.setmHeaderid(1);
        headerListViewModel3.setmHeader("11월 11일 (금)");
        headerListViewModel3.setmEducatorName("강연자 : 김복주 교수");
        headerListViewModel3.setmEducatorTime("강연 시간 : 16:00 ~ 17:00");
        headerListViewModel3.setmEducatorTitle("강연제목 : 실패한 임플란트를 극복하는 smart way");

        headerListViewModel3.setDemoEduImage(R.drawable.demo_header_img3);
        headerListViewModel3.setDemoStateImage(R.drawable.demo_state_img1);
//
        headerListViewModel4.setmHeaderid(2);
        headerListViewModel4.setmHeader("11월 11일 (토)");
        headerListViewModel4.setmEducatorName("강연자 : 김철훈 교수, 이부규 교수, 박홍주 교수");
        headerListViewModel4.setmEducatorTime("강연 시간 : 10:00 ~ 12:00");
        headerListViewModel4.setmEducatorTitle("강연제목 : 보톡스 시술을 시작하기 위한 필수 강연");

        headerListViewModel4.setDemoEduImage(R.drawable.demo_header_img4);
        headerListViewModel4.setDemoStateImage(R.drawable.demo_state_img3);

        mHeaderListViewAdapter.addList(headerListViewModel1);
        mHeaderListViewAdapter.addList(headerListViewModel2);
        mHeaderListViewAdapter.addList(headerListViewModel3);
        mHeaderListViewAdapter.addList(headerListViewModel4);

        mLectureInfoListView.setAdapter(mHeaderListViewAdapter);

        mLectureInfoListView.setOnItemClickListener(onItemClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        measureDisplay();
    }

    private void measureDisplay() {

        Display display = getWindowManager().getDefaultDisplay();

        display.getSize(mSize);

        mDialogFragment1.setOnMeasureDisplay(new AttendDialogFragment1.OnMeasureDisplay() {
            @Override
            public Point onMeasure() {
                return mSize;
            }
        });

    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            callDialogFragment1();
        }//end onItemClick
    };

    private void callDialogFragment1() {
        mDialogFragment1.show(getFragmentManager(), "fragment1");

        mDialogFragment1.setOnDialogFragment1CancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
                callDialogFragment2();
            }
        });
    }

    private void callDialogFragment2(){
        mDialogFragment2.setPoint(mSize);
        mDialogFragment2.show(getFragmentManager(), "fragment2");

        mDialogFragment2.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });
    }



}
