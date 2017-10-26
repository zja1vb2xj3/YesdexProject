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

        mParseManager.setOnCheckAuthenticationCallback(new ParseManager.OnCheckAuthenticationCallback() {
            @Override
            public void onCheckAuthentication(boolean resultSign) {
                Log.i(CLASSNAME, String.valueOf(resultSign));
                if (resultSign == true) {//인증완료
                    startAttendInfoActivity();
                } else {//인증안됨
                    Toast.makeText(getApplicationContext(), "회원정보를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        AttendParseThread parseThread = new AttendParseThread(mParseManager, userName, userNumber);
        parseThread.start();
    }


}
