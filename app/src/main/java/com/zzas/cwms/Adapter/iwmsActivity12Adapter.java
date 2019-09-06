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

public class iwmsActivity12Adapter extends BaseAdapter {
    private List<iwmsActivity12Model> mData;
    private Context mContext;

    public iwmsActivity12Adapter (Context context,List data){
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
        iwmsActivity12Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (iwmsActivity12Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_12item, null);
            holder = new iwmsActivity12Adapter.Holder();
            holder.productName = view.findViewById(R.id.productName);
            holder.sum = view.findViewById(R.id.sum);
            view.setTag(holder);
        }
        iwmsActivity12Model  friend = mData.get(position);
        holder.productName.setText(friend.getProductName());
        holder.productName.setTag(friend.getTag());
        holder.sum.setText(friend.getSum());
        return view;
    }
    private class Holder{
        private TextView productName;
        private TextView sum;
    }
}
