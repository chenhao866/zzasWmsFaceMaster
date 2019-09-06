/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.cwms.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzas.awms.wmsapplication.R;

import java.util.List;

public class lwmsActivity15Adapter extends BaseAdapter {
    private List<lwmsActivity15Model> mData;
    private Context mContext;

    public lwmsActivity15Adapter (Context context,List data){
        this.mData = data;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return  mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =null;
        lwmsActivity15Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (lwmsActivity15Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_15item, null);
            holder = new lwmsActivity15Adapter.Holder();
            holder.moblilesum = view.findViewById(R.id.moblilesum);
            holder.state = view.findViewById(R.id.state);
            view.setTag(holder);
        }
        lwmsActivity15Model  friend = mData.get(position);
        holder.moblilesum.setText(friend.getMoblilesum());
        holder.state.setText("派送中");
        return view;
    }
    private class Holder{
        private TextView moblilesum;
        private TextView state;
    }
}
