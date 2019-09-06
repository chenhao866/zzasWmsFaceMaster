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

public class gwmsActivity10Adapter extends BaseAdapter {
    private List<gwmsActivity10Model> mData;
    private Context mContext;

    public gwmsActivity10Adapter (Context context,List data){
        this.mData = data;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mData.size();
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
        gwmsActivity10Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (gwmsActivity10Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_10item, null);
            holder = new gwmsActivity10Adapter.Holder();
            holder.nameTv = view.findViewById(R.id.nameTv);
            view.setTag(holder);
        }
        gwmsActivity10Model  friend = mData.get(position);
        holder.nameTv.setText(friend.getName());
        holder.nameTv.setTag(friend.getId());
        return view;
    }
    private class Holder{
        public TextView nameTv ;
    }
}
