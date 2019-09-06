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

public class fwmsActivity09aAdapter extends BaseAdapter {
    private List<fwmsActivity09aModel> mData;
    private Context mContext;

    public fwmsActivity09aAdapter (Context context,List data){
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
        fwmsActivity09aAdapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (fwmsActivity09aAdapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_09aitem, null);
            holder = new fwmsActivity09aAdapter.Holder();
            holder.outSum = view.findViewById(R.id.outSum);
            holder.cilentName = view.findViewById(R.id.cilentName);
            holder.productCode = view.findViewById(R.id.productCode);
            holder.productName = view.findViewById(R.id.productName);
            holder.State = view.findViewById(R.id.State);
            view.setTag(holder);
        }
        fwmsActivity09aModel  friend = mData.get(position);
        holder.outSum.setText(friend.getOutSum());
        holder.cilentName.setText(friend.getCilentName());
        holder.productCode.setText(friend.getProductCode());
        holder.productName.setText(friend.getProductName());
        if(friend.getState().equals("1")){
            holder.State.setText("拣货完成");
            int dsf = mContext.getResources().getColor(R.color.zzas10,mContext.getTheme());
            holder.State.setTextColor(dsf);
        }else{
            holder.State.setText("等待拣货");
            int dsf = mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme());
            holder.State.setTextColor(dsf);
        }
        return view;
    }
    private class Holder{
        public TextView outSum;
        public TextView cilentName;
        public TextView productCode;
        public TextView productName;
        public TextView State;
    }
}
