/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.xuexiang.xui.XUI;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.mwmsActivity16aAdapter;
import com.zzas.cwms.Adapter.mwmsActivity16aModel;
import com.zzas.cwms.Adapter.mwmsActivity16bAdapter;
import com.zzas.cwms.Adapter.mwmsActivity16bModel;
import com.zzas.dwms.Beans.wmsaoYunShuBean;
import com.zzas.dwms.Beans.wmsaoYunShuaBean;
import com.zzas.dwms.Beans.wmsaoYunShubBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity16b extends AppCompatActivity implements View.OnClickListener{
    private ImageButton backBut;
    private ListView lv_list;
    private wmsaoYunShubBean ZZaswmsacBean;
    private wmsaoYunShuaBean.outData ZZasBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16b);
        //按钮
        backBut = findViewById(R.id.backBut);
        backBut.setOnClickListener(this);
        //listView
        lv_list = findViewById(R.id.lv_list);


        //页面传值
        ZZasBean = (wmsaoYunShuaBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");
        String trackingnumber = ZZasBean.getTrackingnumber();
        GetYunShubData(trackingnumber);
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            Intent intent=new Intent();
            setResult(1, intent);
            finish();
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetYunShubData(String trackingnumber){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("trackingnumber",trackingnumber);
        rxjavaService .postMapYunShub(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaoYunShubBean>() {
                    @Override
                    public void onNext(wmsaoYunShubBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetYunShubData(weatherEntity);//根据返回数据显示下拉信息
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
    //根据接口数据显示listView
    private void SetYunShubData(wmsaoYunShubBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<mwmsActivity16bModel> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsaoYunShubBean.outData item : ZZaswmsacBean.getOutData()) {
                mwmsActivity16bModel init = new mwmsActivity16bModel();
                init.setProductName(item.getProductname());
                init.setProductCode(item.getProductcode());
                init.setSum(item.getSum());
                init.setDetailedAddress(item.getDetailedaddress());
                lists.add(init);
            }
            mwmsActivity16bAdapter mAdapter = new mwmsActivity16bAdapter(Activity16b.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "当前没有调度商品信息！", Toast.LENGTH_SHORT).show();
        }
    }

}
