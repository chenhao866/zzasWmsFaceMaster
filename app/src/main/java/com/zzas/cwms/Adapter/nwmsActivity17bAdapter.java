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

public class nwmsActivity17bAdapter extends BaseAdapter {
    private List<nwmsActivity17bModel> mData;
    private Context mContext;

    public nwmsActivity17bAdapter (Context context,List data){
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
        nwmsActivity17bAdapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (nwmsActivity17bAdapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_17bitem, null);
            holder = new nwmsActivity17bAdapter.Holder();
            holder.numberid = view.findViewById(R.id.numberid);
            holder.task = view.findViewById(R.id.task);
            view.setTag(holder);
        }
        nwmsActivity17bModel  friend = mData.get(position);
        holder.numberid.setText(friend.getNumberid());
        holder.task.setText(friend.getTask());

        return view;
    }
    private class Holder{
        private TextView numberid;
        private TextView task;
    }
}
