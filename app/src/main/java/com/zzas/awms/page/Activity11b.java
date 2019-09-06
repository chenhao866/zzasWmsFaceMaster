/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.gwmsActivity10aAdapter;
import com.zzas.cwms.Adapter.gwmsActivity10aModel;
import com.zzas.cwms.Adapter.hwmsActivity11Model;
import com.zzas.cwms.Adapter.hwmsActivity11bAdapter;
import com.zzas.cwms.Adapter.hwmsActivity11bModel;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsahOutcomlistBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsaBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsbBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity11b extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageButton backBut;
    private Button SaveBut;
    private ListView lv_list;
    private wmsaiInventoryrecordsaBean.outData ZZasmc_bean;
    private wmsaiInventoryrecordsbBean ZZasOutcomlistBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11b);
        backBut = findViewById(R.id.backBut);
        SaveBut = findViewById(R.id.SaveBut);
        lv_list = findViewById(R.id.lv_list);
        backBut.setOnClickListener(this);
        SaveBut.setOnClickListener(this);
        lv_list.setOnItemClickListener(this);
        ZZasmc_bean = (wmsaiInventoryrecordsaBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");//获取页面传递过来的盘点库位数据
        GetInventoryrecordsbData(ZZasmc_bean.getId());
    }
    //执行获得出库清单详情信息接口
    protected void GetInventoryrecordsbData(String dataid){
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
        rxjavaService .postMapInventoryrecordsb(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaiInventoryrecordsbBean>() {
                    @Override
                    public void onNext(wmsaiInventoryrecordsbBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetInventoryrecordsbData(weatherEntity);//根据返回数据显示下拉信息
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
    //根据接口数据初始化listView
    private void SetInventoryrecordsbData(wmsaiInventoryrecordsbBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            ZZasOutcomlistBean = weatherEntity;
            List<hwmsActivity11bModel> lists = new ArrayList<>();
            for (wmsaiInventoryrecordsbBean.outData item : weatherEntity.getOutData()) {
                hwmsActivity11bModel init = new hwmsActivity11bModel();
                init.setProductCode(item.getProductcode());
                init.setProductName(item.getProductname());
                init.setSpeciOne(item.getSpecione());
                init.setSumcount(item.getSumcount());
                init.setState(item.getState());
                lists.add(init);
            }
            hwmsActivity11bAdapter mAdapter = new hwmsActivity11bAdapter(Activity11b.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            Toast.makeText(getApplicationContext(), "没有出库清单详情信息", Toast.LENGTH_SHORT).show();
        }
    }
    //按钮事件
    @Override
    public void onClick(View v) { //执行返回
        if (v.equals(backBut)){
            setResult(1, null);
            finish();

        }else if (v.equals(SaveBut)){ //执行空库位操作
             if (ZZasOutcomlistBean == null){
                 Gson gson = new Gson();
                 Map<String, wmsaiInventoryrecordsaBean.outData> sfsa = new HashMap<>();
                 ZZasmc_bean.setState("3");
                 sfsa.put("setdata",ZZasmc_bean);
                 String str = gson.toJson(sfsa);
                 UpdateInventoryrecordsVoidData(str);
             }else{
                 Toast.makeText(getApplicationContext(), "库位有商品，无法定义为空", Toast.LENGTH_SHORT).show();
             }
        }
    }
    //执行空库位盘点请求接口
    protected void UpdateInventoryrecordsVoidData(String JsonData){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonData);//將json字符串转换为参数
        rxjavaService .postMapSetPanDianVoid(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsafStolocBean>() {
                    @Override
                    public void onNext(wmsafStolocBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(), "空库位盘点提交成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
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
    //跨页面返回回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            Boolean isQuery = data.getBooleanExtra("isQuery",false);
            if(isQuery){
                GetInventoryrecordsbData(ZZasmc_bean.getId());
            }
        }
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(Activity11b.this,Activity11c.class);
        intent.putExtra("BeanData", ZZasOutcomlistBean.getOutData()[position]);
        startActivityForResult(intent, 1);
    }
}
