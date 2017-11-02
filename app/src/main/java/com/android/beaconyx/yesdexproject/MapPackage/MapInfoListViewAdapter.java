package com.android.beaconyx.yesdexproject.MapPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by beaconyx on 2017-10-23.
 */

/**
 * 마커 클릭 시 나오는 리스트 어뎁터
 */
class MapInfoListViewAdapter extends BaseAdapter {
    private ArrayList<URL> imageUrls;

    private ArrayList<String> titles;
    private ArrayList<String> subtitles;
    private Handler handler;

    MapInfoListViewAdapter() {
        imageUrls = new ArrayList<>();
        titles = new ArrayList<>();
        subtitles = new ArrayList<>();
        handler = new Handler();
    }

    void clearAdapter(){
        if(imageUrls != null || titles != null || subtitles != null) {
            imageUrls.clear();
            titles.clear();
            subtitles.clear();
        }
    }


    void addItem(String imageUrl, String title, String subtitle){
        try {
            if (imageUrl != null || title != null || subtitle != null) {
                URL url = new URL(imageUrl);
                imageUrls.add(url);
                titles.add(title);
                subtitles.add(subtitle);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_row_mapinfo, parent, false);
        }

        final CircleImageView circleImageView = view.findViewById(R.id.circleImageView);
        final TextView title = view.findViewById(R.id.title);
        final TextView subtitle = view.findViewById(R.id.subtitle);

        Thread setListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = imageUrls.get(position).openStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    final String titleStr = titles.get(position);
                    final String subtitleStr = subtitles.get(position);


                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            int bitmapWidth = bitmap.getWidth();
                            int bitmapHeight = bitmap.getHeight();

                            Bitmap resized = null;
                            while (bitmapHeight > 118){
                                resized = Bitmap.createScaledBitmap(bitmap, (bitmapWidth * 118) / bitmapHeight, 118 ,true);
                                bitmapHeight = resized.getHeight();
                                bitmapWidth = resized.getWidth();
                            }

                            circleImageView.setImageBitmap(resized);
                            title.setText(titleStr);
                            subtitle.setText(subtitleStr);
                        }
                    });//end handler
                }//end try
                catch (IOException e){

                }
            }
        });

        setListThread.start();

        return view;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
