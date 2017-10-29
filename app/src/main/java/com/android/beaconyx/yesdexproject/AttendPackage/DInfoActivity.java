package com.android.beaconyx.yesdexproject.AttendPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
    private EditText mUserNameEditText;
    private EditText mUserNumberEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinfo);
        titleInit();
        Log.i(CLASSNAME, "onCreate");

        mThisApplication = (ThisApplication) getApplicationContext();

        mParseController = new AttendParseController();

        mParseController.setOnSearchRegistedUserCallback(onSearchRegistedUserCallback);

        mUserNameEditText = (EditText) findViewById(R.id.userName);
        mUserNumberEditText = (EditText) findViewById(R.id.userNumber);

        final Button authenticationButton = (Button) findViewById(R.id.authenticationButton);

        authenticationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //키보드 내리기
                hideKeyBoard(authenticationButton);
                //
                String userName = mUserNameEditText.getText().toString();
                String userNumber = mUserNumberEditText.getText().toString();

                Log.i(CLASSNAME, userName);
                Log.i(CLASSNAME, userNumber);

                if (userName.equals("") || userNumber.equals("")) {
                    createWrongNotifyDialog("성명과 면허번호를 둘 다 입력 해주세요.");
                } else {
                    String uuid = mThisApplication.getDeviceUUID();

                    //등록된 유저인지 체크
                    CheckRegistedUserThread checkRegistedUserThread = new CheckRegistedUserThread(mParseController, userName, userNumber, uuid);
                    checkRegistedUserThread.start();
                }
            }
        });
    }

    /**
     * 버튼 클릭 시 키보드 내리기
     * @param button
     */
    private void hideKeyBoard(Button button){
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(button.getWindowToken(), 0);
    }

    /**
     * 검색 후 등록된 유저라면 uuid 업데이트 후 콜백
     */
    AttendParseController.OnSearchRegistedUserCallback onSearchRegistedUserCallback = new AttendParseController.OnSearchRegistedUserCallback() {
        @Override
        public void onCheck(int resultSign) {
            if (resultSign == 1) {//등록된 유저가 존재하지 않는다면
                Toast.makeText(getApplicationContext(), "인증 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                startAttendInfoActivity();
            }
            if (resultSign == 2) { //등록된 고객이라면
                //이미 등록된 고객이라고 알려줘야함
                createWrongNotifyDialog("이미 등록된 고객 입니다.");
            }

            if (resultSign == 3) {
                createWrongNotifyDialog("등록되지 않은 고객 입니다.");
            }
        }
    };
    //endregion


    private void createWrongNotifyDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.wrong_notify_dialog_title))
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
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
}





