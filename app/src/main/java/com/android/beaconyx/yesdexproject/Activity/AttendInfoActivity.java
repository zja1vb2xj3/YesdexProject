package com.android.beaconyx.yesdexproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.beaconyx.yesdexproject.Adapter.HeaderListViewAdapter;
import com.android.beaconyx.yesdexproject.Model.HeaderListViewModel;
import com.android.beaconyx.yesdexproject.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AttendInfoActivity extends AppCompatActivity {

    private StickyListHeadersListView mLectureInfoListView;
    private HeaderListViewAdapter mHeaderListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_info);

        mLectureInfoListView = (StickyListHeadersListView) findViewById(R.id.lecture_info_list);
        mHeaderListViewAdapter = new HeaderListViewAdapter(getApplicationContext());

        HeaderListViewModel headerListViewModel1 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel2 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel3 = new HeaderListViewModel();

        headerListViewModel1.setmHeaderid(1);
        headerListViewModel1.setmHeader("11월 10일 (금)");
        headerListViewModel1.setmEducatorName("강연자 : 최점일 교수");
        headerListViewModel1.setmEducatorTime("강연 시간 : 14:00 ~ 15:00");
        headerListViewModel1.setmEducatorTitle("강연제목 : 모든 임상가를 위한...");

        headerListViewModel2.setmHeaderid(1);
        headerListViewModel2.setmHeader("11월 10일 (금)");
        headerListViewModel2.setmEducatorName("강연자 : 손우성 교수");
        headerListViewModel2.setmEducatorTime("강연 시간 : 14:00 ~ 15:00");
        headerListViewModel2.setmEducatorTitle("강연제목 : 치과의사의 직업전문성...");

        headerListViewModel3.setmHeaderid(2);
        headerListViewModel3.setmHeader("11월 11일 (토)");
        headerListViewModel3.setmEducatorName("강연자 : 손우성 교수, 이부규 교수, 박홍주 교수");
        headerListViewModel3.setmEducatorTime("강연 시간 : 10:00 ~ 12:00");
        headerListViewModel3.setmEducatorTitle("강연제목 : 보톡스 시술을 시작하기 위한 필수 강연");

        mHeaderListViewAdapter.addList(headerListViewModel1);
        mHeaderListViewAdapter.addList(headerListViewModel2);
        mHeaderListViewAdapter.addList(headerListViewModel3);

        mLectureInfoListView.setAdapter(mHeaderListViewAdapter);

    }
}
