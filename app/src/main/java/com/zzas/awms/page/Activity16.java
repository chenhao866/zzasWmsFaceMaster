/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.lwmsActivity15Adapter;
import com.zzas.cwms.Adapter.lwmsActivity15Model;
import com.zzas.cwms.Adapter.mwmsActivity16Adapter;
import com.zzas.cwms.Adapter.mwmsActivity16Model;
import com.zzas.dwms.Beans.wmsanPaiSongBean;
import com.zzas.dwms.Beans.wmsaoYunShuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity16 extends AppCompatActivity implements EasyIndicator.onTabClickListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private EasyIndicator easy_indicator;
    private ListView lv_list;
    private ImageButton backBut;
    private wmsaoYunShuBean ZZaswmsacBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16);
        //按钮
        backBut = findViewById(R.id.backBut);
        backBut.setOnClickListener(this);
        //Tab
        easy_indicator = findViewById(R.id.easy_indicator);
        String[] strs = {"装车任务","发车任务"};
        easy_indicator.setTabTitles(strs);
        easy_indicator.setOnTabClickListener(this);
        //listView
        lv_list = findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(this);

        String toporgid = zzasacUserMUO.getOrgTop();
        GetYunShuData(toporgid,"1");
    }
    //Tab点击事件
    @Override
    public void onTabClick(String title, int position) {
        String toporgid = zzasacUserMUO.getOrgTop();
        if (title.equals("装车任务")){
            GetYunShuData(toporgid,"1");
        }else if (title.equals("发车任务")){
            GetYunShuData(toporgid,"2");
        }
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            finish();
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetYunShuData(String toporgid,String state){
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
        myMap.put("state",state);
        rxjavaService .postMapYunShu(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaoYunShuBean>() {
                    @Override
                    public void onNext(wmsaoYunShuBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetYunShuData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetYunShuData(wmsaoYunShuBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<mwmsActivity16Model> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsaoYunShuBean.outData item : ZZaswmsacBean.getOutData()) {
                mwmsActivity16Model init = new mwmsActivity16Model();
                init.setDispatchnumber(item.getDispatchnumber());
                init.setState(item.getState());
                lists.add(init);
            }
            mwmsActivity16Adapter mAdapter = new mwmsActivity16Adapter(Activity16.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "当前没有运输任务！", Toast.LENGTH_SHORT).show();
        }
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = view.findViewById(R.id.state);//通过view找到点击对应行的控件
        String state = tv.getText().toString();
        if (state.equals("待装车")){
            Intent intent=new Intent(Activity16.this,Activity16a.class);
            intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBean.getOutData()[position]);
            intent.putExtra("name","装车");
            startActivityForResult(intent, 1);
        }else if (state.equals("待发车")){
            Intent intent=new Intent(Activity16.this,Activity16a.class);
            intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBean.getOutData()[position]);
            intent.putExtra("name","发车");
            startActivityForResult(intent, 2);
        }
    }
    //接收页面返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1 && data != null){//装车任务
                String toporgid = zzasacUserMUO.getOrgTop();
                GetYunShuData(toporgid,"1");
        }else if(resultCode == 2 && data != null){//发车任务
                String toporgid = zzasacUserMUO.getOrgTop();
                GetYunShuData(toporgid,"2");
        }
    }
}
