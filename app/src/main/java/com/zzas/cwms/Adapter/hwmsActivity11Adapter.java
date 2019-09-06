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

public class hwmsActivity11Adapter extends BaseAdapter {
    private List<hwmsActivity11Model> mData;
    private Context mContext;

    public hwmsActivity11Adapter (Context context,List data){
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
        hwmsActivity11Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (hwmsActivity11Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_11item, null);
            holder = new hwmsActivity11Adapter.Holder();
            holder.snventorynumber = view.findViewById(R.id.snventorynumber);
            holder.inventoryarea = view.findViewById(R.id.inventoryarea);
            holder.state = view.findViewById(R.id.state);
            view.setTag(holder);
        }
        hwmsActivity11Model  friend = mData.get(position);
        holder.snventorynumber.setText(friend.getSnventorynumber());
        holder.inventoryarea.setText(friend.getInventoryarea());
        String str = "盘点中";
        int colorInt = mContext.getResources().getColor(R.color.zzas11,mContext.getTheme());
        if(friend.getState().equals("2")){
            str = "异常";
            colorInt = mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme());
        }
        holder.state.setTextColor(colorInt);
        holder.state.setText(str);
        return view;
    }
    private class Holder{
        private TextView snventorynumber;
        private TextView inventoryarea;
        private TextView state;
    }
}
