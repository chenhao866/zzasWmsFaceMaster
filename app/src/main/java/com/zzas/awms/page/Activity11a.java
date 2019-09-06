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
import com.zzas.cwms.Adapter.gwmsActivity10aAdapter;
import com.zzas.cwms.Adapter.gwmsActivity10aModel;
import com.zzas.cwms.Adapter.hwmsActivity11Adapter;
import com.zzas.cwms.Adapter.hwmsActivity11Model;
import com.zzas.cwms.Adapter.hwmsActivity11aAdapter;
import com.zzas.cwms.Adapter.hwmsActivity11aModel;
import com.zzas.dwms.Beans.wmsagOutlistBean;
import com.zzas.dwms.Beans.wmsahOutcomlistBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsaBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity11a extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageButton backBut;
    private ListView lv_list;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private wmsaiInventoryrecordsBean.outData ZZasmc_bean;
    private  wmsaiInventoryrecordsaBean ZZasOutcomlistBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11a);
        backBut = findViewById(R.id.backBut);
        lv_list = findViewById(R.id.lv_list);
        backBut.setOnClickListener(this);
        lv_list.setOnItemClickListener(this);
        //下拉刷新
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        swipeRefreshLayout.setOnRefreshListener(this);
        ZZasmc_bean = (wmsaiInventoryrecordsBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");//获取页面传递过来的盘点任务数据
        GetInventoryrecordsaData(ZZasmc_bean.getId());
    }
    //执行获得盘点库位信息接口
    protected void GetInventoryrecordsaData(String dataid){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("dataid",dataid);
        rxjavaService .postMapInventoryrecordsa(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaiInventoryrecordsaBean>() {
                    @Override
                    public void onNext(wmsaiInventoryrecordsaBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetInventoryrecordsaData(weatherEntity);//根据返回数据显示下拉信息
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
    //根据接口数据初始化listView
    private void SetInventoryrecordsaData(wmsaiInventoryrecordsaBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<hwmsActivity11aModel> lists = new ArrayList<>();
            ZZasOutcomlistBean = weatherEntity;
            for (wmsaiInventoryrecordsaBean.outData item : ZZasOutcomlistBean.getOutData()) {
                hwmsActivity11aModel init = new hwmsActivity11aModel();
                init.setStorCode(item.getStorcode());
                init.setState(item.getState());
                lists.add(init);
            }
            hwmsActivity11aAdapter mAdapter = new hwmsActivity11aAdapter(Activity11a.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "没有库位盘点信息", Toast.LENGTH_SHORT).show();
        }
    }
    //按钮事件
    @Override
    public void onClick(View v) {
        if(v.equals(backBut)){
            setResult(1, null);
            finish();
        }
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(Activity11a.this,Activity11b.class);
        intent.putExtra("ZZaswmsacBeanItem", ZZasOutcomlistBean.getOutData()[position]);
        startActivityForResult(intent, 1);
    }
    //跨页面返回回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            GetInventoryrecordsaData(ZZasmc_bean.getId());
            Log.e("sss",ZZasmc_bean.getId()+"");
        }
    }
    //下拉刷新事件
    @Override
    public void onRefresh() {
        GetInventoryrecordsaData(ZZasmc_bean.getId());
    }
    //隐藏下拉加载
    private void HideXiaLa(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
