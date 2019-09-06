/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsaoYunShuBean;
import com.zzas.dwms.Beans.wmsaoYunShuaBean;
import com.zzas.dwms.Beans.wmsaoYunShucBean;

import java.util.HashMap;
import java.util.Map;

public class Activity16c extends AppCompatActivity implements View.OnClickListener {
    private SuperButton reCheckBut;
    private SuperButton cancelBut;
    private MaterialEditText dispatchnumber;
    private MaterialEditText dispatchdata;
    private MaterialEditText station;
    private MaterialEditText car;
    private MaterialEditText amountofmoney;
    private MultiLineEditText text;
    private wmsaoYunShuBean.outData ZZasBean;
    private wmsaoYunShucBean ZZasCarData; //车辆信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16c);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        //输入框
        dispatchnumber = findViewById(R.id.dispatchnumber);
        dispatchdata = findViewById(R.id.dispatchdata);
        station = findViewById(R.id.station);
        car = findViewById(R.id.car);
        amountofmoney = findViewById(R.id.amountofmoney);
        text = findViewById(R.id.text);
        //页面传值
        ZZasBean = (wmsaoYunShuBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");
        //初始化页面
        ShowPageFun();
        String id = ZZasBean.getCarid();
        GetYunShucData(id);
    }
    //按钮事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//确认装车
            if (amountofmoney.validate()){
                String json = JsonDataFun();
                SetZhuangCheData(json);
            }
        }else if (v.equals(cancelBut)){//取消
            Intent intent=new Intent();
            Boolean isQuery = false;
            intent.putExtra("isQuery", isQuery);
            setResult(2, intent);
            finish();
        }
    }
    //封装json数据
    private String JsonDataFun() {
        String amountofmoneyStr = amountofmoney.getEditValue();
        String textStr = text.getContentText();
        String receivables = "无";
        String receivablesid = "0";
        if(ZZasCarData.getOutData().length > 0){
            receivablesid = ZZasCarData.getOutData()[0].getId();
            receivables = ZZasCarData.getOutData()[0].getDrivername();

        }
        //开支管理
        Map<String,String> mapOne = new HashMap<>();
        mapOne.put("time",ZZasBean.getDispatchdata());
        mapOne.put("voucherno","1");
        mapOne.put("amountofmoney",amountofmoneyStr);
        mapOne.put("danhao",ZZasBean.getDispatchnumber());
        mapOne.put("storagecharge","-" + amountofmoneyStr);
        mapOne.put("receivables",receivables);
        mapOne.put("text",textStr);
        mapOne.put("creaid", zzasacUserMUO.getUserId());
        mapOne.put("creaorgid",zzasacUserMUO.getUserOrgId());
        mapOne.put("toporgid",zzasacUserMUO.getOrgTop());
        //资金管理
        Map<String,String> mapTwo = new HashMap<>();
        mapTwo.put("time",ZZasBean.getDispatchdata());
        mapTwo.put("expenditure","1");
        mapTwo.put("voucherno","2");
        mapTwo.put("zhichujine",amountofmoneyStr);
        mapTwo.put("storagecharge","-" + amountofmoneyStr);
        mapTwo.put("receivablesid",receivablesid);
        mapTwo.put("receivables",receivables);
        mapTwo.put("text",textStr);
        mapTwo.put("creaid", zzasacUserMUO.getUserId());
        mapTwo.put("creaorgid",zzasacUserMUO.getUserOrgId());
        mapTwo.put("toporgid",zzasacUserMUO.getOrgTop());
        //装车状态
        ZZasBean.setState("2");

        Map<String,Object> map = new HashMap<>();
        map.put("expenditure",mapOne);
        map.put("fundsmanagement",mapTwo);
        map.put("schedulinglist",ZZasBean);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        return str;
    }
    //页面显示值
    private void ShowPageFun(){
        dispatchnumber.setText(ZZasBean.getDispatchnumber());
        dispatchdata.setText(ZZasBean.getDispatchdata());
        station.setText(ZZasBean.getStation());
        car.setText(ZZasBean.getCar());
    }
    //执行获得车辆信息接口
    protected void GetYunShucData(String id){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("id",id);
        rxjavaService .postMapYunShuc(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaoYunShucBean>() {
                    @Override
                    public void onNext(wmsaoYunShucBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            ZZasCarData = weatherEntity;
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
    //执行装车确认请求接口
    protected void SetZhuangCheData(String setdata){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), setdata);//將json字符串转换为参数
        rxjavaService .postMapZhuangCheUpdate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsafStolocBean>() {
                    @Override
                    public void onNext(wmsafStolocBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(),"装车完成", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            Boolean isQuery = true;
                            intent.putExtra("isQuery", isQuery);
                            setResult(2, intent);
                            finish();
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

}
