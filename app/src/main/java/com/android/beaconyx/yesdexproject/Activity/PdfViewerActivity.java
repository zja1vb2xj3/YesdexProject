package com.android.beaconyx.yesdexproject.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;

public class PdfViewerActivity extends Activity {
    private ThisApplication mThisApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        mThisApplication = (ThisApplication) this.getApplicationContext();
        titleInit();
    }

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
}
