package com.android.beaconyx.yesdexproject.MapPackage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Constant.MapPackageConstantPool;
import com.android.beaconyx.yesdexproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MarkerInfoActivity extends Activity {
    private String CLASSNAME = getClass().getSimpleName();
    private FindBeaconContentsModel findBeaconContentsModel;
    private Handler handler = new Handler();
    private URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);

        if(getIntent() != null){
            Intent intent = getIntent();
            findBeaconContentsModel = new FindBeaconContentsModel();
            findBeaconContentsModel = (FindBeaconContentsModel) intent.getSerializableExtra(findBeaconContentsModel.FindBeaconContentsModel_KEY);
        }

        initView();

        titleInit();
    }

    private void initView(){
        final ImageView topImg = findViewById(R.id.topImg);
        final TextView name = findViewById(R.id.name);
        final TextView address = findViewById(R.id.address);
        final TextView homePage = findViewById(R.id.homePage);
        final TextView productExp = findViewById(R.id.productExp);
        final TextView companyExp = findViewById(R.id.companyExp);

        final ImageView phone = findViewById(R.id.phone);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "tel:"+findBeaconContentsModel.getCpyContactNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
                startActivity(intent);
            }
        });

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = homePage.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+str));
                startActivity(intent);
            }
        });


        Thread topImageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(CLASSNAME, findBeaconContentsModel.getCpyAddress());

                    url = new URL(findBeaconContentsModel.getCpyTopImg());

                    InputStream inputStream = url.openStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            topImg.setImageBitmap(bitmap);
                            name.setText(findBeaconContentsModel.getCpyTitle());
                            address.setText(findBeaconContentsModel.getCpyAddress());
                            homePage.setText(findBeaconContentsModel.getCpyHomePage());
                            productExp.setText(findBeaconContentsModel.getSpyProductExp());
                            companyExp.setText(findBeaconContentsModel.getCpyExp());
                        }
                    });//end handler
                }//end try
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        topImageThread.start();

    }


    private void titleInit() {
        View topView = findViewById(R.id.top);

        String titleStr = "";
        if(getIntent() != null){
            titleStr = getIntent().getStringExtra(MapPackageConstantPool.BUSINESS_TITLE_KEY);
        }

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(titleStr);

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
