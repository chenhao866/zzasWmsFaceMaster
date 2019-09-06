/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.cwms.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzas.awms.wmsapplication.R;

import java.util.List;

public class awmsHomeAdapter extends BaseAdapter {

        private List<awmsHomeModel> mData;
        private Context mContext;

        public awmsHomeAdapter (Context context,List data){
            this.mData = data;
            this.mContext = context;
        }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =null;
        Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_02item, null);
            holder = new Holder();
            holder.listLayout =view.findViewById(R.id.listLayout);
            holder.liatImageView =view.findViewById(R.id.liatImageView);
            holder.listTextViem =view.findViewById(R.id.listTextViem);
            view.setTag(holder);
        }
        awmsHomeModel  friend = mData.get(position);
        holder.listLayout.setBackgroundResource(friend.getListLayout());
        holder.liatImageView.setImageResource(friend.getLiatImageView());
        holder.listTextViem.setText(friend.getListTextViem());
        return view;

    }


    private class Holder{
        public LinearLayout listLayout ;
        public ImageView liatImageView;
        public TextView listTextViem ;
    }
}
