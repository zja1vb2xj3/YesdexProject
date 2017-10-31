package com.android.beaconyx.yesdexproject.TabViewPagerPackage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class FInfoActivity extends Activity {

    private SubsamplingScaleImageView zoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finfo);

        titleInit();

        zoomView = (SubsamplingScaleImageView) findViewById(R.id.zoomView);
        zoomView.setImage(ImageSource.resource(R.mipmap.fifth_1));
    }

    private void titleInit(){
        View topView = findViewById(R.id.top);

        TextView title = (TextView) topView.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.f_info_activity_title));

        ImageView back = (ImageView) topView.findViewById(R.id.top_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
