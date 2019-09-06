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

public class nwmsActivity17aAdapter extends BaseAdapter {
    private List<nwmsActivity17aModel> mData;
    private Context mContext;

    public nwmsActivity17aAdapter (Context context,List data){
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
        nwmsActivity17aAdapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (nwmsActivity17aAdapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_17aitem, null);
            holder = new nwmsActivity17aAdapter.Holder();
            holder.storCode = view.findViewById(R.id.storCode);
            holder.wahoName = view.findViewById(R.id.wahoName);
            holder.trayCode = view.findViewById(R.id.trayCode);
            holder.productCode = view.findViewById(R.id.productCode);
            holder.productName = view.findViewById(R.id.productName);
            holder.sum = view.findViewById(R.id.sum);
            holder.creationTime = view.findViewById(R.id.creationTime);
            holder.creaName = view.findViewById(R.id.creaName);
            view.setTag(holder);
        }
        nwmsActivity17aModel  friend = mData.get(position);
        holder.storCode.setText(friend.getStorCode());
        holder.wahoName.setText(friend.getWahoName());
        holder.trayCode.setText(friend.getTrayCode());
        holder.productCode.setText(friend.getProductCode());
        holder.productName.setText(friend.getProductName());
        holder.sum.setText(friend.getSum());
        holder.creationTime.setText(friend.getCreationTime());
        holder.creaName.setText(friend.getCreaName());
        return view;
    }
    private class Holder{
        private TextView storCode;
        private TextView wahoName;
        private TextView trayCode;
        private TextView productCode;
        private TextView productName;
        private TextView sum;
        private TextView creationTime;
        private TextView creaName;
    }
}
