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

import com.android.beaconyx.yesdexproject.Constant.ConstantPool;
import com.android.beaconyx.yesdexproject.ParseController.ParseManager;
import com.android.beaconyx.yesdexproject.R;

public class DInfoActivity extends Activity {
    private ParseManager mParseManager;
    private String CLASSNAME = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinfo);
        titleInit();
        Log.i(CLASSNAME, "onCreate");

        mParseManager = new ParseManager();

        mParseManager.setOnCheckAuthenticationCallback(onCheckAuthenticationCallback);
        mParseManager.setOnUpdateCertificationCallback(onUpdateCertificationCallback);
    }

    ParseManager.OnCheckAuthenticationCallback onCheckAuthenticationCallback = new ParseManager.OnCheckAuthenticationCallback() {
        @Override
        public void onCheckAuthentication(boolean resultSign) {

            Log.i(CLASSNAME, String.valueOf(resultSign));
            if (resultSign == true) {//인증완료
//                    startAttendInfoActivity();
                //TB_Account_Ko의 ACT_CERTIFICATION 을 true 로 바꿔주고
                String getUUID = findUUID();

                if(!getUUID.equals("")){//uuid를 찾았다면
                    Log.i(CLASSNAME + " 인증완료" , getUUID);
                    UpdateCertificationThread certificationThread = new UpdateCertificationThread(mParseManager, getUUID);
                    certificationThread.start();
                }

                else{
                    Log.i(CLASSNAME, "findUUID() 에서 uuid를 못찾음");
                }

            } else {//인증안됨
                Toast.makeText(getApplicationContext(), "회원정보를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
            }

        }
    };

    ParseManager.OnUpdateCertificationCallback onUpdateCertificationCallback = new ParseManager.OnUpdateCertificationCallback() {
        @Override
        public void onUpdate(boolean resultSign) {
            if(resultSign == true){
                Log.i(CLASSNAME, "update성공");
                //TB_USER_KO의 USR_USER_ID에 UUID를 추가 (메인에서 강의 출석 버튼을 누르면 UUID를 검사 해야함)
            }
            else{
                Log.i(CLASSNAME, "update실패");
            }
        }
    };

    private String findUUID(){
        SharedPreferences preferences = getSharedPreferences(ConstantPool.ACCOUNT_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String saveUUID = preferences.getString(ConstantPool.ACCOUNT_SHARED_PREFERENCES_KEY, "");

        return saveUUID;
    }


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

    public void authenticationOperation(View view) {
        EditText userNameEditText = findViewById(R.id.userName);
        EditText userNumberEditText = findViewById(R.id.userNumber);

        String userName = userNameEditText.getText().toString();
        String userNumber = userNumberEditText.getText().toString();

        CheckRegistedUserThread parseThread = new CheckRegistedUserThread(mParseManager, userName, userNumber);
        parseThread.start();
    }


}
