package com.android.beaconyx.yesdexproject.AttendPackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;

public class DInfoActivity extends Activity {
    private String CLASSNAME = getClass().getSimpleName();
    private AttendParseController mParseController;
    private ThisApplication mThisApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinfo);
        titleInit();
        Log.i(CLASSNAME, "onCreate");

        mThisApplication = (ThisApplication) getApplicationContext();

        mParseController = new AttendParseController();

        mParseController.setOnSearchRegistedUserCallback(onSearchRegistedUserCallback);

    }

    /**
     * 인증하기 버튼 클릭
     */
    public void authenticationOperation(View view) {
        EditText userNameEditText = findViewById(R.id.userName);
        EditText userNumberEditText = findViewById(R.id.userNumber);

        String userName = userNameEditText.getText().toString();
        String userNumber = userNumberEditText.getText().toString();

        String uuid = mThisApplication.getDeviceUUID();

        //등록된 유저인지 체크
        CheckRegistedUserThread checkRegistedUserThread = new CheckRegistedUserThread(mParseController, userName, userNumber, uuid);
        checkRegistedUserThread.start();

    }

    /**
     * 검색 후 등록된 유저라면 uuid 업데이트 후 콜백
     */
    AttendParseController.OnSearchRegistedUserCallback onSearchRegistedUserCallback = new AttendParseController.OnSearchRegistedUserCallback() {
        @Override
        public void onCheck(boolean resultSign) {
            if(resultSign == true){//등록된 고객이라면
                startAttendInfoActivity();
            }
            else{
                Toast.makeText(getApplicationContext(), "정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };
    //endregion




    private void startAttendInfoActivity() {
        Intent intent = new Intent(this, AttendInfoActivity.class);
        startActivity(intent);
        finish();
    }


    private void titleInit() {
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.d_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}





