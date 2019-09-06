/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.xuexiang.xui.XUI;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.gwmsActivity10Adapter;
import com.zzas.cwms.Adapter.gwmsActivity10Model;
import com.zzas.cwms.Adapter.hwmsActivity11Adapter;
import com.zzas.cwms.Adapter.hwmsActivity11Model;
import com.zzas.dwms.Beans.wmsagOutlistBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity11 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageButton backBut;
    private ListView lv_list;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private wmsaiInventoryrecordsBean ZZaswmsacBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11);
        backBut = findViewById(R.id.backBut);
        lv_list = findViewById(R.id.lv_list);
        backBut.setOnClickListener(this);
        lv_list.setOnItemClickListener(this);
        //下拉刷新
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        swipeRefreshLayout.setOnRefreshListener(this);
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        GetInventoryrecordsData(topOrg);
    }

    //执行获得盘点任务接口
    protected void GetInventoryrecordsData(String topOrg){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("toporgid",topOrg);
        rxjavaService .postMapInventoryrecords(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaiInventoryrecordsBean>() {
                    @Override
                    public void onNext(wmsaiInventoryrecordsBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetInventoryrecordsData(weatherEntity);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        HideXiaLa();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                        HideXiaLa();
                    }
                });
    }
    //根据接口数据显示listView
    private void SetInventoryrecordsData(wmsaiInventoryrecordsBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<hwmsActivity11Model> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsaiInventoryrecordsBean.outData item : ZZaswmsacBean.getOutData()) {
                hwmsActivity11Model init = new hwmsActivity11Model();
                init.setSnventorynumber(item.getSnventorynumber());
                init.setInventoryarea(item.getInventoryarea());
                init.setState(item.getState());
                lists.add(init);
            }
            hwmsActivity11Adapter mAdapter = new hwmsActivity11Adapter(Activity11.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "没有盘点任务信息", Toast.LENGTH_SHORT).show();
        }
    }
    //子窗口回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
            GetInventoryrecordsData(topOrg);
        }
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if(v.equals(backBut)){
            finish();
        }
    }
    //列表点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转对应页面
        Intent intent=new Intent(Activity11.this,Activity11a.class);
        intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBean.getOutData()[position]);
        startActivityForResult(intent, 1);
    }
    //下拉刷新事件
    @Override
    public void onRefresh() {
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        GetInventoryrecordsData(topOrg);
    }
    //隐藏下拉加载
    private void HideXiaLa(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
