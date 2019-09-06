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

public class mwmsActivity16bAdapter extends BaseAdapter {
    private List<mwmsActivity16bModel> mData;
    private Context mContext;

    public mwmsActivity16bAdapter (Context context,List data){
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
        mwmsActivity16bAdapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (mwmsActivity16bAdapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_16bitem, null);
            holder = new mwmsActivity16bAdapter.Holder();
            holder.productName = view.findViewById(R.id.productName);
            holder.productCode = view.findViewById(R.id.productCode);
            holder.sum = view.findViewById(R.id.sum);
            holder.detailedAddress = view.findViewById(R.id.detailedAddress);
            view.setTag(holder);
        }
        mwmsActivity16bModel  friend = mData.get(position);
        holder.productName.setText(friend.getProductName());
        holder.productCode.setText(friend.getProductCode());
        holder.sum.setText(friend.getSum());
        holder.detailedAddress.setSelected(true);
        holder.detailedAddress.setText(friend.getDetailedAddress());
        return view;
    }
    private class Holder{
        private TextView productName;
        private TextView productCode;
        private TextView sum;
        private TextView detailedAddress;
    }
}
