package com.android.beaconyx.yesdexproject.AttendPackage;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.Constant.SharedPreferencesConstantPool;
import com.android.beaconyx.yesdexproject.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AttendInfoActivity extends FragmentActivity {

    private StickyListHeadersListView mLectureInfoListView;
    private HeaderListViewAdapter mHeaderListViewAdapter;

    private final String CLASSNAME = getClass().getSimpleName();

    private ThisApplication mThisApplication;
    private AttendParseController mParseController;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(CLASSNAME, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_info);

        titleInit();

        initView();

        mThisApplication = (ThisApplication) this.getApplicationContext();
        mThisApplication.setFragmentActivity(this);
        mThisApplication.setFragmentDialogSign(true);

        mParseController = new AttendParseController();
        mParseController.setOnUpdateCertificationCallback(onUpdateCertificationCallback);

        mPreferences = getSharedPreferences(SharedPreferencesConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String certifiValue = mPreferences.getString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, "false");

        Log.i(CLASSNAME, certifiValue);

        //sharePreference certifivalue 체크

        if (certifiValue.equals("true")) {//과거에도 인증
            //Parse TB_Account_Ko Certification true로 변경
            Log.i(CLASSNAME, "과거인증 이력있음");

        } else {//최초 인증
            Log.i(CLASSNAME, "최초인증");
            UpdateCertificationThread updateCertificationThread = new UpdateCertificationThread(mParseController, mThisApplication.getDeviceUUID());
            updateCertificationThread.start();
        }


    }//end onCreate

    //region 최초인증 콜백
    AttendParseController.OnUpdateCertificationCallback onUpdateCertificationCallback = new AttendParseController.OnUpdateCertificationCallback() {
        @Override
        public void onUpdate(String certifiValue) {
            if (certifiValue.equals("true")) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString(SharedPreferencesConstantPool.ACCOUNT_CERTIFICATION_KEY, certifiValue);
                editor.commit();
            } else {
                Log.i(CLASSNAME, "서버 certifiCation 업데이트 실패");
            }
        }
    };

    //endregion


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//                Toast.makeText(getApplicationContext(), "backKey인식", Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CLASSNAME, "onResume");
        mThisApplication.startBeaconThread();
        mThisApplication.setFragmentDialogSign(true); // AttendActivity 실행신호
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(CLASSNAME, "onPause");
        mThisApplication.stopBeaconThread();
    }

    @Override
    protected void onDestroy() {
//        mThisApplication.mTempBeaconID2 = null;
        super.onDestroy();
    }
    /**
     * mLectureInfoListView onItemClickListener
     */
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            callDialogFragment3(i);
        }//end onItemClick
    };

    private void callDialogFragment3(int position) {
        ArrayList<String> time = new ArrayList<>();
        time.add("11월 10일 (금) 14:00 ~ 15:00");
        time.add("11월 10일 (금) 15:00 ~ 16:00");
        time.add("11월 10일 (금) 16:00 ~ 17:00");
        time.add("11월 11일 (토) 10:10 ~ 12:00");

        ArrayList<String> location = new ArrayList<>();
        location.add("제 2 전시장 (신관) 1층 제3 강의장");
        location.add("제 2 전시장 (신관) 1층 제3 강의장");
        location.add("제 2 전시장 (신관) 1층 제3 강의장");
        location.add("제 1 전시장 (본관) 2층 Main 강의장");

        ArrayList<String> name = new ArrayList<>();
        name.add("최점일 교수");
        name.add("손우성 교수");
        name.add("김복주 교수");
        name.add("김철훈, 이부규, 박홍주 교수");

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.mipmap.attend_1);
        images.add(R.mipmap.attend_2);
        images.add(R.mipmap.attend_3);
        images.add(R.mipmap.attend_4);

        ArrayList<String> profiles = new ArrayList<>();

        profiles.add(
                "1977 서울대학교 치과대학 졸업\n" +
                        "1980 서울대학교 병원 치주과 전공의 수료\n" +
                        "1983-현재 부산대학교 치의학전문대학원 치주과 교수"
        );

        profiles.add(
                "1981년 서울대학교 치과대학 졸업\n" +
                        "1984년 서울대학교병원 치과교정과 전공의 수료\n" +
                        "1990년 서울대학교 대학원 치의학박사(치과교정학)\n" +
                        "1987-현재 부산대학교 치과대학 치과교정학 교수\n" +
                        "1990년 Chicago Children’s Memorial Hospital방문,연수 (Cleft Palate Team) \n" +
                        "1992년 일본 큐슈대학 치학부 방문,연수(Cleft Palate Team) \n" +
                        "2003년 부산대학교병원 치과진료처장\n" +
                        "2005년 부산대학교 치과대학 학장,치의학전문대학원장\n" +
                        "2003년 부산대학교 경영대학원 최고경영자 과정(45기) 수료\n" +
                        "2005년 부산대학교 의과대학 의료경영자 과정(1기)  수료\n" +
                        "2008년 대한치과교정학회 부산-경남-울산 지회장\n" +
                        "2013년 대한구순구개열학회 부회장"
        );//손우성
        profiles.add(
                "현)동아대학교 의료원 치과학교실 구강악안면외과 조교수\n" +
                        "부산대학교 치과대학 졸업\n" +
                        "부산대학교 치과대학 구강악안면외과학 석.박사\n" +
                        "동아대학교 의료원 치과학교실 구강악안면외과 레지던트 수료\n" +
                        "Korean Board of Oral and maxillofacial surgery\n" +
                        "국군 부산병원 치과 구강악안면외과 과장\n" +
                        "동아임상치의학 연구회 기획이사\n" +
                        "2016-2017 UCLA Oral surgery, Visiting Scholar"
        );//김복주

        profiles.add("");


        AttendDialogFragment3 dialogFragment3 = AttendDialogFragment3.newInstance();

        dialogFragment3.setData(time.get(position), location.get(position), name.get(position), images.get(position), profiles.get(position));

        dialogFragment3.show(getFragmentManager(), "fragment1");

        dialogFragment3.setOnDialogFragment3CancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });
    }

    /**
     * Title View 초기설정
     */
    private void titleInit() {
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.attend_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * View Item 초기설정
     */
    private void initView() {

        mLectureInfoListView = (StickyListHeadersListView) findViewById(R.id.lecture_info_list);
        mHeaderListViewAdapter = new HeaderListViewAdapter(getApplicationContext());

        HeaderListViewModel headerListViewModel1 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel2 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel3 = new HeaderListViewModel();
        HeaderListViewModel headerListViewModel4 = new HeaderListViewModel();
//
        //강연 장소 추가
        headerListViewModel1.setmHeaderid(1);
        headerListViewModel1.setmHeader("11월 10일 (금)");
        headerListViewModel1.setmEducatorName("강연자 : 최점일 교수");
        headerListViewModel1.setmEducatorTime("강연 시간 : 14:00 ~ 15:00");
        headerListViewModel1.setmEducatorLocation("강연 장소 : 제 2 전시장 (신관) 1층 제3 강의장");
        headerListViewModel1.setmEducatorTitle("강연 제목 : 모든 임상가를 위한 peri_implantitls의\n비외과적 접근법");

        headerListViewModel1.setDemoEduImage(R.mipmap.attend_1);
        headerListViewModel1.setDemoStateImage(R.mipmap.demo_state_img1);
//
        headerListViewModel2.setmHeaderid(1);
        headerListViewModel2.setmHeader("11월 10일 (금)");
        headerListViewModel2.setmEducatorName("강연자 : 손우성 교수");
        headerListViewModel2.setmEducatorTime("강연 시간 : 15:00 ~ 16:00");
        headerListViewModel2.setmEducatorLocation("강연 장소 : 제 2 전시장 (신관) 1층 제3 강의장");
        headerListViewModel2.setmEducatorTitle("강연 제목 : 치과의사의 직업전문성");

        headerListViewModel2.setDemoEduImage(R.mipmap.attend_2);
        headerListViewModel2.setDemoStateImage(R.mipmap.demo_state_img2);
//
        headerListViewModel3.setmHeaderid(1);
        headerListViewModel3.setmHeader("11월 11일 (금)");
        headerListViewModel3.setmEducatorName("강연자 : 김복주 교수");
        headerListViewModel3.setmEducatorTime("강연 시간 : 16:00 ~ 17:00");
        headerListViewModel3.setmEducatorLocation("강연 장소 : 제 2 전시장 (신관) 1층 제3 강의장");
        headerListViewModel3.setmEducatorTitle("강연 제목 : 실패한 임플란트를 극복하는 smart way");

        headerListViewModel3.setDemoEduImage(R.mipmap.attend_3);
        headerListViewModel3.setDemoStateImage(R.mipmap.demo_state_img1);
//
        headerListViewModel4.setmHeaderid(2);
        headerListViewModel4.setmHeader("11월 11일 (토)");
        headerListViewModel4.setmEducatorName("강연자 : 김철훈 교수, 이부규 교수, 박홍주 교수");
        headerListViewModel4.setmEducatorTime("강연 시간 : 10:00 ~ 12:00");
        headerListViewModel1.setmEducatorLocation("강연 장소 : 제 1 전시장 (본관) 2층 Main 강의장");
        headerListViewModel4.setmEducatorTitle("강연 제목 : 보톡스 시술을 시작하기 위한 필수 강연");

        headerListViewModel4.setDemoEduImage(R.mipmap.attend_4);
        headerListViewModel4.setDemoStateImage(R.mipmap.demo_state_img3);

        mHeaderListViewAdapter.addList(headerListViewModel1);
        mHeaderListViewAdapter.addList(headerListViewModel2);
        mHeaderListViewAdapter.addList(headerListViewModel3);
        mHeaderListViewAdapter.addList(headerListViewModel4);

        mLectureInfoListView.setAdapter(mHeaderListViewAdapter);

        mLectureInfoListView.setOnItemClickListener(onItemClickListener);

    }//end initView


}//end Activity class
