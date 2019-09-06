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

public class jwmsActivity13aAdapter extends BaseAdapter {
    private List<jwmsActivity13aModel> mData;
    private Context mContext;

    public jwmsActivity13aAdapter (Context context,List data){
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
        jwmsActivity13aAdapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (jwmsActivity13aAdapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_13aitem, null);
            holder = new jwmsActivity13aAdapter.Holder();
            holder.number = view.findViewById(R.id.number);
            holder.state = view.findViewById(R.id.state);
            view.setTag(holder);
        }
        jwmsActivity13aModel  friend = mData.get(position);

        holder.number.setText(friend.getNumber());

        String str = "巡检中";
        int coloSum = mContext.getResources().getColor(R.color.zzas11,mContext.getTheme());
        if (friend.getState().equals("2")){
            str = "异常";
            coloSum = mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme());
        }else if (friend.getState().equals("1")){
            str = "正常";
            coloSum = mContext.getResources().getColor(R.color.zzas10,mContext.getTheme());
        }
        holder.state.setTextColor(coloSum);
        holder.state.setText(str);

        return view;
    }
    private class Holder{
        private TextView number;
        private TextView state;
    }
}
