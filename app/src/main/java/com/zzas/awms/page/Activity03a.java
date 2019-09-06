/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsaaClientBean;
import com.zzas.dwms.Beans.wmsabwahoBean;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public  class Activity03a extends AppCompatActivity implements  View.OnClickListener, MaterialSpinner.OnItemSelectedListener {
    private MaterialSpinner clientSpinner;
    private MaterialSpinner warehouseSpinner;
    private Button yesBut;
    private Button noBut;
    private String clientID = "";
    private wmsaaClientBean.outData[] clientOutData;
    private String wahoID = "";
    private wmsabwahoBean.outData[] wahoOutData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03a);
        clientSpinner = findViewById(R.id.clientSpinner);
        warehouseSpinner = findViewById(R.id.warehouseSpinner);
        yesBut = findViewById(R.id.yesBut);
        noBut = findViewById(R.id.noBut);

        //初始化下拉框ui
        clientSpinner.setOnItemSelectedListener(this);
        warehouseSpinner.setOnItemSelectedListener(this);
        //初始化按钮ui
        yesBut.setOnClickListener(this);
        noBut.setOnClickListener(this);
        //初始化下拉框数据
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        GetCilenData(topOrg);
    }
    //按钮事件
    @Override
    public void onClick(View v) {
        if(v.equals(yesBut)){
            Intent intent=new Intent();
            Boolean isQuery = true;
            intent.putExtra("isQuery", isQuery);
            intent.putExtra("clientID", clientID);
            intent.putExtra("wahoID", wahoID);
            setResult(1, intent);
            finish();
        }else if (v.equals(noBut)){
            Intent intent=new Intent();
            setResult(1, intent);
            finish();
        }
    }
    //执行获得客户信息接口
    protected void GetCilenData(String topOrg){
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
        rxjavaService .postMapCilen(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaaClientBean>() {
                    @Override
                    public void onNext(wmsaaClientBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetCilenData(weatherEntity);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
                        GetWahoData(topOrg);
                    }
                });
    }
    //客户下拉框写入数据
    private void SetCilenData(wmsaaClientBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            clientOutData = weatherEntity.getOutData();
            ArrayList<String> list = new ArrayList<>();
            list.add("全部");
            for (wmsaaClientBean.outData item : clientOutData) {
                list.add(item.getCilentname());
            }
            clientSpinner.setItems(list);
        }else {
            Toast.makeText(getApplicationContext(), "没有客户信息", Toast.LENGTH_SHORT).show();
        }
    }
    //执行获得仓库信息接口
    protected void GetWahoData(String topOrg){
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
        rxjavaService .postMapWaho(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsabwahoBean>() {
                    @Override
                    public void onNext(wmsabwahoBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetWahoData(weatherEntity);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                    }
                });
    }
    //仓库下拉框写入数据
    private void SetWahoData(wmsabwahoBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            wahoOutData = weatherEntity.getOutData();
            ArrayList<String> list = new ArrayList<>();
            list.add("全部");
            for (wmsabwahoBean.outData item : wahoOutData) {
                list.add(item.getWahoname());
            }
            warehouseSpinner.setItems(list);
        }else {
            Toast.makeText(getApplicationContext(), "没有仓库信息", Toast.LENGTH_SHORT).show();
        }
    }
    //下拉框回调函数
    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        if(view.equals(clientSpinner)){
            if(position == 0){
                clientID = "";
            }else if(position > 0){
                int dataId = clientOutData[position-1].getId();
                clientID = dataId+"";
            }
        }else if (view.equals(warehouseSpinner)){
            if(position == 0){
                wahoID = "";
            }else if(position > 0){
                int dataId = wahoOutData[position-1].getId();
                wahoID = dataId+"";
            }
        }
    }
}
