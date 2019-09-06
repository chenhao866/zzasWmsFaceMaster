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

public class fwmsActivity09Adapter extends BaseAdapter {
    private List<fwmsActivity09Model> mData;
    private Context mContext;

    public fwmsActivity09Adapter (Context context,List data){
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
        fwmsActivity09Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (fwmsActivity09Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_09item, null);
            holder = new fwmsActivity09Adapter.Holder();
            holder.nameTv = view.findViewById(R.id.nameTv);
            view.setTag(holder);
        }
        fwmsActivity09Model  friend = mData.get(position);
        holder.nameTv.setText(friend.getName());
        holder.nameTv.setTag(friend.getId());
        return view;
    }
    private class Holder{
        public TextView nameTv ;
    }
}
