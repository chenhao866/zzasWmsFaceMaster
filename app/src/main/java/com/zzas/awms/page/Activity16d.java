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
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsaoYunShuBean;

import java.util.HashMap;
import java.util.Map;


public class Activity16d extends AppCompatActivity implements View.OnClickListener {
    private SuperButton reCheckBut;
    private SuperButton cancelBut;
    private MaterialEditText dispatchnumber;
    private MaterialEditText dispatchdata;
    private MaterialEditText station;
    private MaterialEditText car;
    private wmsaoYunShuBean.outData ZZasBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16d);
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
        //页面传值
        ZZasBean = (wmsaoYunShuBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");
        //初始化页面
        ShowPageFun();
    }
    //页面显示值
    private void ShowPageFun(){
        dispatchnumber.setText(ZZasBean.getDispatchnumber());
        dispatchdata.setText(ZZasBean.getDispatchdata());
        station.setText(ZZasBean.getStation());
        car.setText(ZZasBean.getCar());
    }
    //按钮事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//确认发车
                String json = JsonDataFun();
                SetFaCheData(json);
        }else if (v.equals(cancelBut)){//取消
            Intent intent=new Intent();
            Boolean isQuery = false;
            intent.putExtra("isQuery", isQuery);
            setResult(3, intent);
            finish();
        }
    }
    //封装json数据
    private String JsonDataFun() {
        wmsaoYunShuBean.outData temBean = new wmsaoYunShuBean.outData();
        Map<String,Object> map = new HashMap<>();
        //装车状态
        ZZasBean.setState("3");
        map.put("schedulinglist",ZZasBean);
        temBean.setDispatchnumber(ZZasBean.getDispatchnumber());
        temBean.setDispatchtype(ZZasBean.getDispatchtype());
        temBean.setDispatchdata(ZZasBean.getDispatchdata());
        temBean.setStationid(ZZasBean.getStationid());
        temBean.setStation(ZZasBean.getStation());
        temBean.setCarid(ZZasBean.getCarid());
        temBean.setCar(ZZasBean.getCar());
        temBean.setState("1");
        temBean.setCreaid(zzasacUserMUO.getUserId());
        temBean.setCreaorgid(zzasacUserMUO.getUserOrgId());
        temBean.setToporgid(zzasacUserMUO.getOrgTop());
        map.put("departurelist",temBean);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        return str;
    }
    //执行发车确认请求接口
    protected void SetFaCheData(String setdata){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), setdata);//將json字符串转换为参数
        rxjavaService .postMapFaCheUpdate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsafStolocBean>() {
                    @Override
                    public void onNext(wmsafStolocBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(),"发车完成", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            Boolean isQuery = true;
                            intent.putExtra("isQuery", isQuery);
                            setResult(3, intent);
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
