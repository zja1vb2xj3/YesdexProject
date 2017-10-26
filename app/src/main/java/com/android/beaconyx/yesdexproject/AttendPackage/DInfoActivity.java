package com.android.beaconyx.yesdexproject.AttendPackage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.Constant.SharedPreferencesConstantPool;
import com.android.beaconyx.yesdexproject.ParseController.ParseManager;
import com.android.beaconyx.yesdexproject.R;

public class DInfoActivity extends Activity {
    private String CLASSNAME = getClass().getSimpleName();
    private ParseManager mParseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinfo);
        titleInit();
        Log.i(CLASSNAME, "onCreate");

        mParseManager = new ParseManager();

        mParseManager.setOnCheckAuthenticationCallback(onCheckAuthenticationCallback);//등록된 유저인지 판단 userNumber를 가지고 있음
        mParseManager.setOnUpdateCertificationCallback(onUpdateCertificationCallback);
        mParseManager.setOnUpdateUSR_User_IdCallback(onUpdateUSR_user_idCallback);

    }

    //region 인증완료 후 Account_Ko 해당 uuid의 certification을 true 교체
    ParseManager.OnCheckAuthenticationCallback onCheckAuthenticationCallback = new ParseManager.OnCheckAuthenticationCallback() {
        @Override
        public void onCheckAuthentication(String userNumber,boolean resultSign) {

            Log.i(CLASSNAME, String.valueOf(resultSign));
            if (resultSign == true) {//인증완료
                String getUUID = findUUID();

                if (!getUUID.equals("")) {//uuid를 찾았다면
                    Log.i(CLASSNAME + " 인증완료", getUUID);
                    UpdateCertificationThread certificationThread = new UpdateCertificationThread(mParseManager, getUUID, userNumber);
                    certificationThread.start();
                } else {
                    Log.i(CLASSNAME, "findUUID() 에서 uuid를 못찾음");
                }

            } else {//인증안됨
                Toast.makeText(getApplicationContext(), "회원정보를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
            }

        }
    };
    //endregion

    //region Account_Ko 해당 uuid의 certification을 true 교체 후
    ParseManager.OnUpdateCertificationCallback onUpdateCertificationCallback = new ParseManager.OnUpdateCertificationCallback() {
        @Override
        public void onUpdate(String uuid, String userNumber, boolean resultSign) {
            if (resultSign == true) {
                Log.i(CLASSNAME, "update성공");
                //TB_USER_KO의 USR_USER_ID에 UUID를 추가
                UpdateUserIdThread updateUserIdThread = new UpdateUserIdThread(mParseManager, uuid, userNumber);
                updateUserIdThread.start();

            } else {
                Log.i(CLASSNAME, "update실패");
            }
        }
    };
    //endregion

    //region TB_User_Ko의 USR_USER_ID에 해당 디바이스 uuid update 후 AttendActivity 화면전환
    ParseManager.OnUpdateUSR_USER_IDCallback onUpdateUSR_user_idCallback = new ParseManager.OnUpdateUSR_USER_IDCallback() {
        @Override
        public void onUpdate(boolean resultSign) {
            if(resultSign == true){//업데이트 성공
                startAttendInfoActivity();
            }
            else{
                Log.i(CLASSNAME +"\n" + onUpdateUSR_user_idCallback.toString(), "업데이트 실패");
            }
        }
    };
    //endregion

    private String findUUID() {
        SharedPreferences mSharedPreferences = getSharedPreferences(SharedPreferencesConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String saveUUID = mSharedPreferences.getString(SharedPreferencesConstantPool.ACCOUNT_SHARED_PREFERENCES_KEY, "");

        return saveUUID;
    }


    private void startAttendInfoActivity() {
        Intent intent = new Intent(this, AttendInfoActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 인증하기 버튼 클릭
     */
    public void authenticationOperation(View view) {
        EditText userNameEditText = findViewById(R.id.userName);
        EditText userNumberEditText = findViewById(R.id.userNumber);

        String userName = userNameEditText.getText().toString();
        String userNumber = userNumberEditText.getText().toString();

        //등록된 유저인지 체크
        CheckRegistedUserThread parseThread = new CheckRegistedUserThread(mParseManager, userName, userNumber);
        parseThread.start();

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
