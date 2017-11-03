package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Application.BeaconContentsModel;
import com.android.beaconyx.yesdexproject.Application.BeaconContentsModelPool;
import com.android.beaconyx.yesdexproject.Application.BeaconParseController;
import com.android.beaconyx.yesdexproject.Application.SearchBeaconContentsListThread;
import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.Constant.MapPackageConstantPool;
import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;

public class MapInfoActivity extends Activity {

    private MapView mMapView;

    private final String ACTIVITY_NAME = "MapInfoActivity";
    private ThisApplication mThisApplication;
    private BeaconParseController beaconParseController;

    private ListView mMarkerInfoListView;
    private ImageView mListHideImage;

    private String CLASSNAME = getClass().getSimpleName();
    private MapInfoListViewAdapter listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapinfo);

        mThisApplication = (ThisApplication) this.getApplicationContext();
        mThisApplication.setOnBeaconEventCallBack(onBeaconEventCallBack);

        titleInit();

        mapInit();

        beaconParseController = new BeaconParseController();
        beaconParseController.setOnFindBeaconContentsCallBack(onFindBeaconContentsCallBack);

        mMarkerInfoListView = (ListView) findViewById(R.id.listview);
        listViewAdapter = new MapInfoListViewAdapter(getApplicationContext());

        mListHideImage = (ImageView) findViewById(R.id.listHideImage);
        mListHideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listLayoutUp();
            }
        });

    }

    ThisApplication.OnBeaconEventCallBack onBeaconEventCallBack = new ThisApplication.OnBeaconEventCallBack() {
        @Override
        public void onBeaconEvent(BeaconContentsModel beaconContentsModel) {
            if (beaconContentsModel != null) {
                String beaconID = beaconContentsModel.getBeaconID();//받아온 idx로

                SearchBeaconContentsListThread thread = new SearchBeaconContentsListThread(beaconParseController, beaconID);
                thread.start();
            }
        }
    };

    //region foreach
    //                for (FindBeaconContentModel findBeaconContentModel : beaconContentModels) {
//                    Toast.makeText(getApplicationContext(), String.valueOf(findBeaconContentModel.getBeaconMinor()), Toast.LENGTH_SHORT).show();
//                    Log.i(CLASSNAME, findBeaconContentModel.getBeaconMinor());
//                }
    //endregion

    //region onFindBeaconContentsCallBack
    BeaconParseController.OnFindBeaconContentsListCallBack onFindBeaconContentsCallBack = new BeaconParseController.OnFindBeaconContentsListCallBack() {
        @Override
        public void onFindContentsList(ArrayList<FindBeaconContentsModel> findBeaconContentModels, boolean resultSign) {

            String beaconMinor = "";

            if (resultSign == true && findBeaconContentModels != null) {

                for (int i = 0; i < findBeaconContentModels.size(); i++) {
                    beaconMinor = findBeaconContentModels.get(0).getCpyBeaconMinor();
                }
            }

            createListView(findBeaconContentModels);

            BeaconContentsModelPool beaconContentsModelPool = BeaconContentsModelPool.getInstance();
            ArrayList<BeaconContentsModel> beaconContentsModels = beaconContentsModelPool.getBeaconContentsModels();

            for (int i = 0; i < beaconContentsModels.size(); i++) {
                if (beaconContentsModels.get(i).getBeaconMinor().equals(beaconMinor)) {
                    beaconContentsModels.get(i).setIsnotify(true);
                } else {
                    beaconContentsModels.get(i).setIsnotify(false);
                }
            }

            Log.i(CLASSNAME, "onFindBeaconContentsCallBack");
            mMapView.setMarkerList(beaconContentsModels);
            mMapView.invalidate();
        }
    };

    //endregion


    private void mapInit() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMapView = (MapView) findViewById(R.id.mapview);
                mMapView.setOnMarkerTouchListener(onMarkerTouchListener);
                Display display = getWindowManager().getDefaultDisplay();

                Point displaySize = new Point();

                display.getSize(displaySize);

                mMapView.setOriginalMapViewSIze(2850, 4752);

                mMapView.setImage(ImageSource.resource(R.mipmap.map_img));

                BeaconContentsModelPool beaconContentsModelPool = BeaconContentsModelPool.getInstance();
                ArrayList<BeaconContentsModel> beaconContentsModels = beaconContentsModelPool.getBeaconContentsModels();

                if (beaconContentsModels != null) {
                    mMapView.setMarkerList(beaconContentsModels);
                    mMapView.invalidate();
                }

            }
        });
    }

    //비콘 반응이 있다면 콜백을 받아서 받은 콜백 데이터로 파스에 검색


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume");
        mThisApplication.startBeaconThread();

    }//MapActivity화면 true 및 Thread start

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause");
        mThisApplication.stopBeaconThread(); // AttendActivity 실행신호

    }//MapActivity화면 false 및 Thread stop

    @Override
    protected void onDestroy() {
        mThisApplication.mTempBeaconID = null;
        super.onDestroy();
    }

    MapView.OnMarkerTouchListener onMarkerTouchListener = new MapView.OnMarkerTouchListener() {
        @Override
        public void onMarkerTouch(BeaconContentsModel model) {

//             listViewAdapter.addItem(findBeaconContentsModel.getCpyThumbnailImg(), findBeaconContentsModel.getCpyTitle(), findBeaconContentsModel.getCpyAddress());
            FindBeaconContentsModel findBeaconContentsModel = new FindBeaconContentsModel();
            findBeaconContentsModel.setCpyThumbnailImg("http://beaconyx.co.kr/YESDEX/Company/n_26.jpg");
            findBeaconContentsModel.setCpyTitle("(주)신흥");
            findBeaconContentsModel.setCpyAddress("서울시 중구 청파로 450 (중림동)");

            ArrayList<FindBeaconContentsModel> findBeaconContentModels = new ArrayList<>();
            findBeaconContentModels.add(findBeaconContentsModel);

            createListView(findBeaconContentModels);

        }
    };



    private void createListView(ArrayList<FindBeaconContentsModel> findBeaconContentsModels) {

        listViewAdapter.clearAdapter();

        for (FindBeaconContentsModel findBeaconContentsModel : findBeaconContentsModels) {
            if (findBeaconContentsModel.getCpyThumbnailImg() != null)
                listViewAdapter.addItem(findBeaconContentsModel.getCpyThumbnailImg(), findBeaconContentsModel.getCpyTitle(), findBeaconContentsModel.getCpyAddress());
            else {
                Log.i(CLASSNAME, "createListView param is data null");
            }
        }


        mMarkerInfoListView.setAdapter(listViewAdapter);

        listLayoutDown();

        mMarkerInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FindBeaconContentsModel findBeaconContentsModel = new FindBeaconContentsModel();
                findBeaconContentsModel.setCpyTitle("㈜신흥");
                findBeaconContentsModel.setCpyAddress("서울시 중구 청파로 450 (중림동)");
                findBeaconContentsModel.setCpyContactNumber("02-6366-2000");
                findBeaconContentsModel.setCpyHomePage("www.shinhung.co.kr");
                findBeaconContentsModel.setCpyTopImg("http://beaconyx.co.kr/YESDEX/Company/t_26.png");
                findBeaconContentsModel.setSpyProductExp("치과용 진료 장비 (치과용 체어 & 유니트 시스템)\n" +
                        "치과용 임플란트\n" +
                        "치과용 귀금속 합금\n" +
                        "치과용 유치관\n" +
                        "치과 장비 액세서리 및 장비 부속 관련품\n" +
                        "치과용 엑스레이 장비\n" +
                        "치과용 캐비닛 시스템 \n" +
                        "치과용 일회용 주사침 \n" +
                        "치과용 위생용품\n" +
                        "치과용 골이식재\n" +
                        "치과 진료용 재료\n" +
                        "진단 시스템\n" +
                        "구강 카메라 시스템\n" +
                        "이미징 시스템\n" +
                        "증기 소독기\n" +
                        "초음파 장비\n" +
                        "소독 및 유지 보수 장비\n" +
                        "세라믹 퍼네이스 및 재료\n" +
                        "보험청구 프로그램\n" +
                        "기타");

                findBeaconContentsModel.setCpyExp("주식회사 신흥은 1955년에 창립되어 지난 62년간 대한민국 치과산업의 발전을 선도해온 국내 제1의 기업입니다. \n" +
                        "\n" +
                        "주요 제품으로는 끊임없는 기술혁신으로 치과용 유니트 체어, 치과용 임플란트, 치과용 귀금속 합금, 유치관, 치과용 주사침 등을 연구개발/생산하고 있으며 각 지역본부 및 광범위한 유통망을 통해 각 지역본부 및 전국 사무소 네트워크와 첨단시설의 대규모 물류센터를 통해 6천 여 종, 2만 여 가지 치과 기자재를 신속하고 정확하게 공급하고 있습니다. \n" +
                        "\n" +
                        "또한 신흥은 지난해 2015년 치과계 교육과 교류의 장인 신흥양지연수원을 개원하여 치과계 세미나 및 학문 발전에 이바지하고 더 나아가 치의학 발전을 도모하고 있습니다. \n" +
                        "\n" +
                        "신흥은 국내 치과업계 최초로 국제적인 제품 인증인 UL, CE 마크를 취득하였으며 이외 의료기기 인증을 통해 품질경영 능력과 제품 안전성 및 우수성을 인정 받았습니다. 1990년 국내 의료 업체로서는 최초로 기업공개를 하였으며 62년이라는 업력에서 알 수 있듯이 한 길만을 우직하게 걸어온 치과기자재 제조 및 유통 전문 기업입니다.\n");


                Intent intent = new Intent(getApplicationContext(), MarkerInfoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(findBeaconContentsModel.FindBeaconContentsModel_KEY, findBeaconContentsModel);
                intent.putExtras(bundle);

                //엑티비티 타이틀
                TextView title = view.findViewById(R.id.title);

                String titleStr = title.getText().toString();

                intent.putExtra(MapPackageConstantPool.BUSINESS_TITLE_KEY, titleStr);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });


    }

    private void listLayoutDown() {
        Animation animaion = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        animaion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mMarkerInfoListView.setVisibility(View.VISIBLE);
                mListHideImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mListHideImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mMarkerInfoListView.startAnimation(animaion);

    }

    private void listLayoutUp() {
        Animation animaion = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        animaion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mListHideImage.startAnimation(animation);
                mMarkerInfoListView.setVisibility(View.INVISIBLE);
                mListHideImage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mMarkerInfoListView.startAnimation(animaion);
    }

    private void titleInit() {
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.map_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
