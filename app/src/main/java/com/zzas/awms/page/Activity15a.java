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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.dwms.Beans.wmsamAnQuanaBean;
import com.zzas.dwms.Beans.wmsanPaiSongBean;

import java.util.HashMap;
import java.util.Map;

public class Activity15a extends AppCompatActivity implements View.OnClickListener {
    private SuperButton reCheckBut;
    private SuperButton cancelBut;

    private TextView moblilesum;
    private TextView unit;
    private TextView shippingaddress;
    private TextView consignee;
    private TextView contact;
    private TextView productName;
    private TextView number;
    private MultiLineEditText remarks;
    private wmsanPaiSongBean.outData ZZasBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_15a);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        //textView
        moblilesum = findViewById(R.id.moblilesum);
        unit = findViewById(R.id.unit);
        shippingaddress = findViewById(R.id.shippingaddress);
        consignee = findViewById(R.id.consignee);
        contact = findViewById(R.id.contact);
        productName = findViewById(R.id.productName);
        number = findViewById(R.id.number);
        //多文本输入框
        remarks= findViewById(R.id.remarks);
        //页面传值
        ZZasBean = (wmsanPaiSongBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");
        SetPageShowFun();
    }
    //初始化页面显示
    private void SetPageShowFun(){
        moblilesum.setText(ZZasBean.getMoblilesum());
        unit.setText(ZZasBean.getUnit());
        shippingaddress.setText(ZZasBean.getShippingaddress());
        consignee.setText(ZZasBean.getConsignee());
        contact.setText(ZZasBean.getContact());
        productName.setText(ZZasBean.getProductname());
        number.setText(ZZasBean.getNumber());
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//执行确认派送
            String json = JsonDataStr();
            UpdatePaiSongData(json);
        }else if (v.equals(cancelBut)){//取消
            Intent intent=new Intent();
            Boolean isQuery = false;
            intent.putExtra("isQuery", isQuery);
            setResult(1, intent);
            finish();
        }
    }
    //组装json字符串
    private String JsonDataStr() {
        ZZasBean.setRemarks(remarks.getContentText());
        Map<String,Object> map = new HashMap<>();
        ZZasBean.setState("2");
        map.put("setdata",ZZasBean);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        return str;
    }
    //执行派送确认请求接口
    protected void UpdatePaiSongData(String setdata){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), setdata);//將json字符串转换为参数
        rxjavaService .postMapPaiSongUpdate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsajAdjustBean>() {
                    @Override
                    public void onNext(wmsajAdjustBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(), "派送完成", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            Boolean isQuery = true;
                            intent.putExtra("isQuery", isQuery);
                            setResult(1, intent);
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
