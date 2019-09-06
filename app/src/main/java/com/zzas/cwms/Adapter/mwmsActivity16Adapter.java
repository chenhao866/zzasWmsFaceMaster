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

public class mwmsActivity16Adapter extends BaseAdapter {
    private List<mwmsActivity16Model> mData;
    private Context mContext;

    public mwmsActivity16Adapter (Context context,List data){
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
        mwmsActivity16Adapter.Holder holder = null;
        if(convertView != null){
            view = convertView;
            holder = (mwmsActivity16Adapter.Holder)view.getTag();
        }else{
            view = View.inflate(mContext, R.layout.activity_16item, null);
            holder = new mwmsActivity16Adapter.Holder();
            holder.dispatchnumber = view.findViewById(R.id.dispatchnumber);
            holder.state = view.findViewById(R.id.state);
            view.setTag(holder);
        }
        mwmsActivity16Model  friend = mData.get(position);
        holder.dispatchnumber.setText(friend.getDispatchnumber());
        if (friend.getState().equals("1")){
            holder.state.setText("待装车");
        }else if (friend.getState().equals("2")){
            holder.state.setText("待发车");
        }

        return view;
    }
    private class Holder{
        private TextView dispatchnumber;
        private TextView state;
    }
}
