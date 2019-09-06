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

import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xui.XUI;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.jwmsActivity13bAdapter;
import com.zzas.cwms.Adapter.jwmsActivity13bModel;
import com.zzas.cwms.Adapter.kwmsActivity14bAdapter;
import com.zzas.cwms.Adapter.kwmsActivity14bModel;
import com.zzas.dwms.Beans.wmsalShenBeiaBean;
import com.zzas.dwms.Beans.wmsalShenBeibBean;
import com.zzas.dwms.Beans.wmsamAnQuanaBean;
import com.zzas.dwms.Beans.wmsamAnQuanbBean;
import com.zzas.ewms.Tools.zzasToolBdialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity14b extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageButton backBut;
    private ListView lv_list;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private wmsamAnQuanaBean.outData ZZasmc_bean;
    private wmsamAnQuanbBean ZZaswmsacBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_14b);
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
        //页面传值
        ZZasmc_bean = (wmsamAnQuanaBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");
        String uuid = ZZasmc_bean.getDataid();
        GetAnQuanbData(uuid);
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
    //接收页面返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null){
            Boolean isQuery =  data.getBooleanExtra("isQuery",false);
            if (isQuery){
                String uuid = ZZasmc_bean.getDataid();
                GetAnQuanbData(uuid);
            }
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetAnQuanbData(String uuid){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("uuid",uuid);
        rxjavaService .postMapAnQuanb(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsamAnQuanbBean>() {
                    @Override
                    public void onNext(wmsamAnQuanbBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetAnQuanbData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetAnQuanbData(wmsamAnQuanbBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<kwmsActivity14bModel> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsamAnQuanbBean.outData item : ZZaswmsacBean.getOutData()) {
                kwmsActivity14bModel init = new kwmsActivity14bModel();
                init.setTask(item.getTask());
                init.setState(item.getState());
                lists.add(init);
            }
            kwmsActivity14bAdapter mAdapter = new kwmsActivity14bAdapter(Activity14b.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "当前没有巡检任务！", Toast.LENGTH_SHORT).show();
        }
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(Activity14b.this,Activity14c.class);
        intent.putExtra("checkrecorda",ZZasmc_bean);
        intent.putExtra("checkrecordb",ZZaswmsacBean.getOutData()[position]);
        startActivityForResult(intent, 1);
    }
    //下拉刷新事件
    @Override
    public void onRefresh() {
        String uuid = ZZasmc_bean.getDataid();
        GetAnQuanbData(uuid);
    }
    //隐藏下拉加载
    private void HideXiaLa(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
