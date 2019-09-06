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

public class cwmsActivity04Adapter extends BaseAdapter {

    private List<cwmsActivity04Model> mData;
    private Context mContext;

    public cwmsActivity04Adapter  (Context context,List data){
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
        cwmsActivity04Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (cwmsActivity04Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_04item, null);
            holder = new cwmsActivity04Adapter.Holder();
            holder.enterSum = view.findViewById(R.id.enterSum);
            holder.cilentName = view.findViewById(R.id.cilentName);
            holder.productCode = view.findViewById(R.id.productCode);
            holder.productName = view.findViewById(R.id.productName);
            holder.predictSum = view.findViewById(R.id.predictSum);
            holder.predictTime = view.findViewById(R.id.predictTime);
            holder.difference = view.findViewById(R.id.difference);
            holder.State = view.findViewById(R.id.State);
            view.setTag(holder);
        }
        cwmsActivity04Model  friend = mData.get(position);
        holder.enterSum.setText(friend.getEnterSum());
        holder.cilentName.setText(friend.getCilentName());
        holder.productCode.setText(friend.getProductCode());
        holder.productName.setText(friend.getProductName());
        holder.predictSum.setText(friend.getPredictSum());
        holder.predictTime.setText(friend.getPredictTime());
        holder.difference.setText(friend.getDifference());

        if(friend.getState().equals("0")){
            holder.State.setText("未复核待入库");
            int dsf = mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme());
            holder.State.setTextColor(dsf);
        }else if (friend.getState().equals("2")){
            holder.State.setText("已复核待入库");
            int dsf = mContext.getResources().getColor(R.color.zzas10,mContext.getTheme());
            holder.State.setTextColor(dsf);
        }
        return view;
    }
    private class Holder{
        public TextView enterSum;
        public TextView cilentName;
        public TextView productCode;
        public TextView productName;
        public TextView predictSum;
        public TextView predictTime;
        public TextView difference;
        public TextView State;
    }
}
