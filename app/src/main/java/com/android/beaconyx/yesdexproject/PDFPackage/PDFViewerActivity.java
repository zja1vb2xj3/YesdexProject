package com.android.beaconyx.yesdexproject.PDFPackage;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;
import com.joanzapata.pdfview.PDFView;

import java.io.File;


public class PDFViewerActivity extends Activity {
    private ThisApplication mThisApplication;
    private LinearLayout mPDFLayout;

    private File mPDFFile;
    private String mPDFFileName;

    private final String PDFNAME = "http://www.beaconyx.co.kr/YESDEX/Lecturer/20171110/1/book.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        mThisApplication = (ThisApplication) this.getApplicationContext();
        titleInit();

        PDFView pdfview = findViewById(R.id.pdfview);

        pdfview.fromAsset("sample.pdf").defaultPage(1).showMinimap(false).enableSwipe(true).load();

    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mThisApplication.setFragmentDialogSign(true);
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
                mThisApplication.setFragmentDialogSign(true);
                finish();
            }
        });
    }


}
