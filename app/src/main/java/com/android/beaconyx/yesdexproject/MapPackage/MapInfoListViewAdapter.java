package com.android.beaconyx.yesdexproject.MapPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.R;
import com.bumptech.glide.Glide;

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
    private Context context;
    MapInfoListViewAdapter(Context context) {
        imageUrls = new ArrayList<>();
        titles = new ArrayList<>();
        subtitles = new ArrayList<>();
        this.context = context;
    }

    void clearAdapter() {
        if (imageUrls != null || titles != null || subtitles != null) {
            imageUrls.clear();
            titles.clear();
            subtitles.clear();
        }
    }


    void addItem(String imageUrl, String title, String subtitle) {
        try {
            if (imageUrl != null || title != null || subtitle != null) {
                URL url = new URL(imageUrl);
                imageUrls.add(url);
                titles.add(title);
                subtitles.add(subtitle);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


        MapInfoListViewHolder mapInfoListViewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_row_mapinfo, parent, false);
            mapInfoListViewHolder = new MapInfoListViewHolder();
            mapInfoListViewHolder.circleImageView = view.findViewById(R.id.circleImageView);
            mapInfoListViewHolder.title = view.findViewById(R.id.title);
            mapInfoListViewHolder.subtitle = view.findViewById(R.id.subtitle);


            view.setTag(mapInfoListViewHolder);
        }
        else{
            mapInfoListViewHolder = (MapInfoListViewHolder) view.getTag();
        }

        final String titleStr = titles.get(position);
        final String subtitleStr = subtitles.get(position);

        Glide.with(context).load(imageUrls.get(position)).into(mapInfoListViewHolder.circleImageView);
        mapInfoListViewHolder.title.setText(titleStr);
        mapInfoListViewHolder.subtitle.setText(subtitleStr);

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


    private class MapInfoListViewHolder {
        CircleImageView circleImageView;
        TextView title;
        TextView subtitle;
    }
}
