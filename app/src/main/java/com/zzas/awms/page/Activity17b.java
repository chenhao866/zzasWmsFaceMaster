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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.xuexiang.xui.XUI;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.nwmsActivity17aAdapter;
import com.zzas.cwms.Adapter.nwmsActivity17aModel;
import com.zzas.cwms.Adapter.nwmsActivity17bAdapter;
import com.zzas.cwms.Adapter.nwmsActivity17bModel;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.dwms.Beans.wmsapSheBeiItemBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity17b extends AppCompatActivity implements View.OnClickListener {
    private ImageButton backBut;
    private ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_17b);
        //按钮
        backBut = findViewById(R.id.backBut);
        backBut.setOnClickListener(this);
        //listview
        lv_list = findViewById(R.id.lv_list);
        //获取页面传值
        String number =  getIntent().getStringExtra("number");
        String toporgid = zzasacUserMUO.getOrgTop();
        GetSheBeiItemData(toporgid,number);
    }
    //按钮事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            finish();
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetSheBeiItemData(String toporgid,String  number){
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
        myMap.put("number",number);
        rxjavaService .postMapSheBeiItem(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsapSheBeiItemBean>() {
                    @Override
                    public void onNext(wmsapSheBeiItemBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetSheBeiItemData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetSheBeiItemData(wmsapSheBeiItemBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<nwmsActivity17bModel> lists = new ArrayList<>();
            int sumIndex = 1;
            for (wmsapSheBeiItemBean.outData item : weatherEntity.getOutData()) {
                nwmsActivity17bModel init = new nwmsActivity17bModel();
                init.setNumberid(sumIndex+"");
                init.setTask(item.getTask());
                lists.add(init);
                sumIndex++;
            }
            nwmsActivity17bAdapter mAdapter = new nwmsActivity17bAdapter(Activity17b.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "设备信息为空！", Toast.LENGTH_SHORT).show();
        }
    }
}
