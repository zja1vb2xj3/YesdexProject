package com.android.beaconyx.yesdexproject.MapPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.R;

import java.util.ArrayList;

/**
 * Created by beaconyx on 2017-10-23.
 */

public class MapInfoListViewAdapter extends BaseAdapter {
    private ArrayList<String> rowItems;

    public MapInfoListViewAdapter(){
        this.rowItems = new ArrayList<>();
    }

    public void addItem(String rowItem){
        rowItems.add(rowItem);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_row_mapinfo, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.listText);

        String data = rowItems.get(position);

        textView.setText(data);

        return view;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
