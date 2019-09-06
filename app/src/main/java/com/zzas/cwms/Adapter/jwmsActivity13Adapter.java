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

public class jwmsActivity13Adapter extends BaseAdapter {
    private List<jwmsActivity13Model> mData;
    private Context mContext;

    public jwmsActivity13Adapter (Context context,List data){
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =null;
        jwmsActivity13Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (jwmsActivity13Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_13item, null);
            holder = new jwmsActivity13Adapter.Holder();
            holder.checkType = view.findViewById(R.id.checkType);
            holder.State = view.findViewById(R.id.State);
            view.setTag(holder);
        }
        jwmsActivity13Model  friend = mData.get(position);
        String type = "日巡检";
        if (friend.getCheckType().equals("2")){
            type = "周巡检";
        }else if (friend.getCheckType().equals("3")){
            type = "月巡检";
        }
        holder.checkType.setText(type);

        String str = "巡检中";
        int coloSum = mContext.getResources().getColor(R.color.zzas11,mContext.getTheme());
        if (friend.getState().equals("2")){
            str = "异常";
            coloSum = mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme());
        }
        holder.State.setTextColor(coloSum);
        holder.State.setText(str);

        return view;
    }
    private class Holder{
        private TextView checkType;
        private TextView State;
    }
}
