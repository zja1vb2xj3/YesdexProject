package com.android.beaconyx.yesdexproject.PDFPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;

import java.io.File;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class PDFViewerActivity extends Activity implements DownloadFile.Listener {
    private ThisApplication mThisApplication;
    private LinearLayout mPDFLayout;

    private PDFViewPager mPDFViewPager;
    private RemotePDFViewPager mRemotePDFViewPager;
    private PDFPagerAdapter mPDFPagerAdapter;

    private File mPDFFile;
    private String mPDFFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        mThisApplication = (ThisApplication) this.getApplicationContext();
        titleInit();


    }

    private void checkVersion(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


            alertDialogBuilder
                    .setMessage(getString(R.string.pdf_not_support_message))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.pdf_not_dialog_button),
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    // 프로그램을 종료한다
                                    finish();
                                }
                            }).show();



        }else {
//            //pdf 데이터를 받아옴
//            Intent intent = getIntent();
//            mDtoContent = (DtoContent) intent.getSerializableExtra(BcxConstant.CommonConstant.DTO_CONTENT);
//            mPDFLayout = (LinearLayout) findViewById(R.id.pdf_layout);
//
//            mPDFFile = new File(getFilesDir().getPath() + "/" + BcxConstant.FolderName.DIMF_CATALOG + "/" + CommManager.getAndroidSettingLanguage(PdfViewActivity.this));
//            String[] pdfPath = mDtoContent.getContentPdf().split("/");
//            mPDFFileName = pdfPath[pdfPath.length - 1];
//            handler.sendEmptyMessage(0);

        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(mPDFFileName != null){

//                File file = new File(getFilesDir().getPath() + "/" + )

            }


        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Toast.makeText(getApplicationContext(), "backKey인식", Toast.LENGTH_SHORT).show();
                mThisApplication.setFragmentDialog1Sign(true);

                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void titleInit() {
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.pdf_viewer_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onSuccess(String url, String destinationPath) {

    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
}
