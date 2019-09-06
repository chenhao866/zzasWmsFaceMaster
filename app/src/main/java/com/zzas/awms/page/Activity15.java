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
import com.zzas.cwms.Adapter.kwmsActivity14Adapter;
import com.zzas.cwms.Adapter.kwmsActivity14Model;
import com.zzas.cwms.Adapter.lwmsActivity15Adapter;
import com.zzas.cwms.Adapter.lwmsActivity15Model;
import com.zzas.dwms.Beans.wmsamAnQuanBean;
import com.zzas.dwms.Beans.wmsanPaiSongBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity15 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageButton backBut;
    private ListView lv_list;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private wmsanPaiSongBean ZZaswmsacBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_15);
        //按钮
        backBut = findViewById(R.id.backBut);
        backBut.setOnClickListener(this);
        //列表
        lv_list = findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(this);
        //下拉刷新
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        swipeRefreshLayout.setOnRefreshListener(this);
        String toporgid = zzasacUserMUO.getOrgTop();
        GetPaiSongData(toporgid);
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            finish();
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetPaiSongData(String toporgid){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("toporgid",toporgid);
        rxjavaService .postMapPaiSong(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsanPaiSongBean>() {
                    @Override
                    public void onNext(wmsanPaiSongBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetPaiSongData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetPaiSongData(wmsanPaiSongBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<lwmsActivity15Model> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsanPaiSongBean.outData item : ZZaswmsacBean.getOutData()) {
                lwmsActivity15Model init = new lwmsActivity15Model();
                init.setMoblilesum(item.getMoblilesum());
                lists.add(init);
            }
            lwmsActivity15Adapter mAdapter = new lwmsActivity15Adapter(Activity15.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "当前没有派送任务！", Toast.LENGTH_SHORT).show();
        }
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(Activity15.this,Activity15a.class);
        intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBean.getOutData()[position]);
        startActivityForResult(intent, 1);
    }
    //接收页面返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1 && data != null){
            Boolean isQuery =  data.getBooleanExtra("isQuery",false);
            if (isQuery){
                String toporgid = zzasacUserMUO.getOrgTop();
                GetPaiSongData(toporgid);
            }
        }
    }
    //下拉刷新事件
    @Override
    public void onRefresh() {
        String toporgid = zzasacUserMUO.getOrgTop();
        GetPaiSongData(toporgid);
    }
    //隐藏下拉加载
    private void HideXiaLa(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
