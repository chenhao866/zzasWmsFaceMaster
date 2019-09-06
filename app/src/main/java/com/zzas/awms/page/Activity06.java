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

import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.bwmsActivity03Adapter;
import com.zzas.cwms.Adapter.bwmsActivity03Model;
import com.zzas.cwms.Adapter.dwmsActivity06Adapter;
import com.zzas.cwms.Adapter.dwmsActivity06Model;
import com.zzas.dwms.Beans.wmsacEnterlistBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity06 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageButton backBut;
    private ImageButton screenBut;
    private ListView lv_list;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private wmsacEnterlistBean ZZaswmsacBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_06);
        backBut = findViewById(R.id.backBut);
        screenBut = findViewById(R.id.screenBut);
        lv_list = findViewById(R.id.lv_list);
        backBut.setOnClickListener(this);
        screenBut.setOnClickListener(this);
        lv_list.setOnItemClickListener(this);
        //下拉刷新
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        swipeRefreshLayout.setOnRefreshListener(this);
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        GetEnterlistData(topOrg,"","");
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if(v.equals(backBut)){
            finish();
        }else if (v.equals(screenBut)){
            Intent intent=new Intent(Activity06.this,Activity06a.class);
            startActivityForResult(intent, 1);
        }
    }
    //子窗口回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Boolean IsQuery =  data.getBooleanExtra("isQuery",false);
            if(IsQuery){
                String clientID = data.getStringExtra("clientID");
                String wahoID = data.getStringExtra("wahoID");
                String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
                GetEnterlistData(topOrg,clientID,wahoID);
            }else {
            }
        }else if(requestCode == 2){
            String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
            GetEnterlistData(topOrg,"","");
        }
    }
    //执行获得入库清单信息接口
    protected void GetEnterlistData(String topOrg,String cilentid,String wahoid){
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
        myMap.put("cilentid",cilentid);
        myMap.put("wahoid",wahoid);
        myMap.put("state","2");
        rxjavaService .postMapEntrlist(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsacEnterlistBean>() {
                    @Override
                    public void onNext(wmsacEnterlistBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetEnterlistData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetEnterlistData(wmsacEnterlistBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<dwmsActivity06Model> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsacEnterlistBean.outData item : ZZaswmsacBean.getOutData()) {
                dwmsActivity06Model init = new dwmsActivity06Model();
                init.setId(item.getId());
                init.setName(item.getEntersum());
                lists.add(init);
            }
            dwmsActivity06Adapter mAdapter = new dwmsActivity06Adapter(Activity06.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "没有待上架订单", Toast.LENGTH_SHORT).show();
        }
    }
    //listview点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//跳转对应页面
        Intent intent=new Intent(Activity06.this,Activity07.class);
        intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBean.getOutData()[position]);
        startActivityForResult(intent, 2);
    }

    //下拉刷新事件
    @Override
    public void onRefresh() {
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        GetEnterlistData(topOrg,"","");
    }
    //隐藏下拉加载
    private void HideXiaLa(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
