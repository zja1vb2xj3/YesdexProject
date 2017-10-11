package com.android.beaconyx.yesdexproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Model.HeaderListViewModel;
import com.android.beaconyx.yesdexproject.R;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by beaconyx on 2017-10-11.
 */

public class HeaderListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private ArrayList<HeaderListViewModel> mHeaderListModels;

    private LayoutInflater mInflater;

    public HeaderListViewAdapter(Context context) {
        mHeaderListModels = new ArrayList<>();
        mInflater = LayoutInflater.from(context);

    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();

            convertView = mInflater.inflate(R.layout.headerlistview_header, parent, false);

            holder.sectionText = (TextView) convertView.findViewById(R.id.header);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = mHeaderListModels.get(position).getmHeader();
        holder.sectionText.setText(headerText);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {

        return mHeaderListModels.get(position).getmHeaderid();
    }

    public void addList(HeaderListViewModel headerListViewModel) {
        mHeaderListModels.add(headerListViewModel);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewHolder holder = null;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.headerlistview_row, parent, false);
            holder = new ListViewHolder();

            holder.attendStateImageView = (ImageView) convertView.findViewById(R.id.attend_state_image);
            holder.educatorImageView = (ImageView) convertView.findViewById(R.id.educator_image);
            holder.educatorNameView = (AutofitTextView) convertView.findViewById(R.id.educator_name);
            holder.educatorTimeView = (AutofitTextView) convertView.findViewById(R.id.educator_time);
            holder.educatorTitleView = (AutofitTextView) convertView.findViewById(R.id.educator_title);

            convertView.setTag(holder);

        } else {
            holder = (ListViewHolder) convertView.getTag();
        }

        HeaderListViewModel model = mHeaderListModels.get(position);

        if (mHeaderListModels.get(position).getmEducatorName() != null) {

            String educatorName = model.getmEducatorName();
            holder.educatorNameView.setText(educatorName);
        }

        if (mHeaderListModels.get(position).getmEducatorTime() != null) {
            String educatorTime = model.getmEducatorTime();
            holder.educatorTimeView.setText(educatorTime);
        }

        if (mHeaderListModels.get(position).getmEducatorTitle() != null) {
            String educatorTitle = model.getmEducatorTitle();
            holder.educatorTitleView.setText(educatorTitle);
        }

        return convertView;
    }


    @Override
    public int getCount() {
        return mHeaderListModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mHeaderListModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class HeaderViewHolder {
        TextView sectionText;
    }

    private class ListViewHolder {
        ImageView attendStateImageView;
        ImageView educatorImageView;
        AutofitTextView educatorNameView;
        AutofitTextView educatorTimeView;
        AutofitTextView educatorTitleView;
    }


}
