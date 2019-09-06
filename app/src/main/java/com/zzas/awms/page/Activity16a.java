/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.Nullable;
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
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.mwmsActivity16Adapter;
import com.zzas.cwms.Adapter.mwmsActivity16Model;
import com.zzas.cwms.Adapter.mwmsActivity16aAdapter;
import com.zzas.cwms.Adapter.mwmsActivity16aModel;
import com.zzas.dwms.Beans.wmsanPaiSongBean;
import com.zzas.dwms.Beans.wmsaoYunShuBean;
import com.zzas.dwms.Beans.wmsaoYunShuaBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity16a extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button installBut;
    private ImageButton backBut;
    private ListView lv_list;
    private wmsaoYunShuaBean ZZaswmsacBean;
    private wmsaoYunShuBean.outData ZZasBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16a);
        //按钮
        backBut = findViewById(R.id.backBut);
        installBut = findViewById(R.id.installBut);
        backBut.setOnClickListener(this);
        installBut.setOnClickListener(this);
        //listView
        lv_list = findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(this);

        //页面传值
        ZZasBean = (wmsaoYunShuBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");
        String dispatchnumber = ZZasBean.getDispatchnumber();
        GetYunShuaData(dispatchnumber);
        //按钮赋值
        String str = getIntent().getStringExtra("name");
        installBut.setText(str);
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            Intent intent=new Intent();
            setResult(1, intent);
            if(installBut.getText().equals("发车")){
                setResult(2, intent);
            }
            finish();
        }else if (v.equals(installBut)){
                if(installBut.getText().equals("装车")){
                    Intent intent=new Intent(Activity16a.this,Activity16c.class);
                    intent.putExtra("ZZaswmsacBeanItem",ZZasBean);
                    startActivityForResult(intent, 2);
                }else if(installBut.getText().equals("发车")){
                    Intent intent=new Intent(Activity16a.this,Activity16d.class);
                    intent.putExtra("ZZaswmsacBeanItem",ZZasBean);
                    startActivityForResult(intent, 3);
            }
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetYunShuaData(String dispatchnumber){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("dispatchnumber",dispatchnumber);
        rxjavaService .postMapYunShua(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaoYunShuaBean>() {
                    @Override
                    public void onNext(wmsaoYunShuaBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetYunShuaData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetYunShuaData(wmsaoYunShuaBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<mwmsActivity16aModel> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsaoYunShuaBean.outData item : ZZaswmsacBean.getOutData()) {
                mwmsActivity16aModel init = new mwmsActivity16aModel();
                init.setCilentName(item.getCilentname());
                init.setCilentSum(item.getCilentsum());
                init.setTransportationType(item.getTransportationtype());
                init.setWaybillType(item.getWaybilltype());
                init.setDetailedAddress(item.getDetailedaddress());
                lists.add(init);
            }
            mwmsActivity16aAdapter mAdapter = new mwmsActivity16aAdapter(Activity16a.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "当前没有调度客户信息！", Toast.LENGTH_SHORT).show();
        }
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(Activity16a.this,Activity16b.class);
        intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBean.getOutData()[position]);
        startActivityForResult(intent, 1);
    }
    //接收页面返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2 && data != null){//装车任务
            Boolean isQuery =  data.getBooleanExtra("isQuery",false);
            if(isQuery){
                Intent intent=new Intent();
                setResult(1, intent);
                finish();
            }
        }else if(resultCode == 3 && data != null) {//发车任务
            Boolean isQuery =  data.getBooleanExtra("isQuery",false);
            if (isQuery) {
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
            }
        }
    }
}
