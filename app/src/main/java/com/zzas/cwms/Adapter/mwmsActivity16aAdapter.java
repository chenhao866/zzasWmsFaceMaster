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

public class mwmsActivity16aAdapter extends BaseAdapter {

    private List<mwmsActivity16aModel> mData;
    private Context mContext;

    public mwmsActivity16aAdapter (Context context,List data){
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
        mwmsActivity16aAdapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (mwmsActivity16aAdapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_16aitem, null);
            holder = new mwmsActivity16aAdapter.Holder();
            holder.cilentName = view.findViewById(R.id.cilentName);
            holder.cilentSum = view.findViewById(R.id.cilentSum);
            holder.transportationType = view.findViewById(R.id.transportationType);
            holder.waybillType = view.findViewById(R.id.waybillType);
            holder.detailedAddress = view.findViewById(R.id.detailedAddress);
            view.setTag(holder);
        }
        mwmsActivity16aModel  friend = mData.get(position);
        holder.cilentName.setText(friend.getCilentName());
        holder.cilentSum.setText(friend.getCilentSum());
        holder.transportationType.setText(friend.getTransportationType());
        holder.waybillType.setText(friend.getWaybillType());
        holder.detailedAddress.setSelected(true);
        holder.detailedAddress.setText(friend.getDetailedAddress());
        return view;
    }
    private class Holder{
        private TextView cilentName;
        private TextView cilentSum;
        private TextView transportationType;
        private TextView waybillType;
        private TextView detailedAddress;
    }
}
