/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.cwms.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzas.awms.wmsapplication.R;

import java.util.List;

public class owmsActivity19Adapter extends BaseAdapter {
    private List<owmsActivity19Model> mData;
    private Context mContext;

    public owmsActivity19Adapter (Context context,List data){
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
        owmsActivity19Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (owmsActivity19Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_19item, null);
            holder = new owmsActivity19Adapter.Holder();
            holder.LayoutOne = view.findViewById(R.id.LayoutOne);
            holder.LayoutTwo = view.findViewById(R.id.LayoutTwo);
            holder.textYuYin = view.findViewById(R.id.textYuYin);
            holder.KeHuYuYin = view.findViewById(R.id.KeHuYuYin);
            view.setTag(holder);
        }
        owmsActivity19Model  friend = mData.get(position);
        if (friend.getLayoutOne().equals("1")){
            holder.LayoutOne.setVisibility(View.VISIBLE);
            holder.textYuYin.setText(friend.getTextYuYin());
        }
        if (friend.getLayoutTwo().equals("1")){
            holder.LayoutTwo.setVisibility(View.VISIBLE);
            holder.KeHuYuYin.setText(friend.getKeHuYuYin());
        }
        return view;
    }
    private class Holder{
        private LinearLayout LayoutOne;
        private LinearLayout LayoutTwo;
        private TextView textYuYin;
        private TextView KeHuYuYin;
    }
}
