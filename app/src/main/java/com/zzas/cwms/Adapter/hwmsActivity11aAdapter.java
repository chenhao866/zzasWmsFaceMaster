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

public class hwmsActivity11aAdapter extends BaseAdapter {
    private List<hwmsActivity11aModel> mData;
    private Context mContext;

    public hwmsActivity11aAdapter (Context context,List data){
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
        hwmsActivity11aAdapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (hwmsActivity11aAdapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_11aitem, null);
            holder = new hwmsActivity11aAdapter.Holder();
            holder.storCode = view.findViewById(R.id.storCode);
            holder.state = view.findViewById(R.id.state);
            view.setTag(holder);
        }
        hwmsActivity11aModel  friend = mData.get(position);
        holder.storCode.setText(friend.getStorCode());
        String str = "盘点中";
        int colorInt = mContext.getResources().getColor(R.color.zzas11,mContext.getTheme());
        if(friend.getState().equals("2")){
            colorInt = mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme());
            str = "异常";
        }else if(friend.getState().equals("3")){
            colorInt = mContext.getResources().getColor(R.color.zzas10,mContext.getTheme());
            str = "已盘点";
        }
        holder.state.setTextColor(colorInt);
        holder.state.setText(str);
        return view;
    }
    private class Holder{
        private TextView storCode;
        private TextView state;
    }
}
