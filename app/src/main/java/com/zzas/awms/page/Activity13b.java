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
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.jwmsActivity13aAdapter;
import com.zzas.cwms.Adapter.jwmsActivity13aModel;
import com.zzas.cwms.Adapter.jwmsActivity13bAdapter;
import com.zzas.cwms.Adapter.jwmsActivity13bModel;
import com.zzas.dwms.Beans.wmsalShenBeiBean;
import com.zzas.dwms.Beans.wmsalShenBeiaBean;
import com.zzas.dwms.Beans.wmsalShenBeibBean;
import com.zzas.ewms.Tools.zzasToolBdialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity13b extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageButton backBut;
    private ImageButton qrBut;
    private ListView lv_list;
    private int REQUEST_CODE = 111;//用于二维码扫描
    private Intent IntentData;//二维码扫描结果
    private wmsalShenBeiaBean.outData ZZasmc_bean;
    private wmsalShenBeibBean ZZaswmsacBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_13b);
        //按钮
        backBut = findViewById(R.id.backBut);
        qrBut = findViewById(R.id.qrBut);
        backBut.setOnClickListener(this);
        qrBut.setOnClickListener(this);
        //列表
        lv_list = findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(this);
        //页面传值
        ZZasmc_bean = (wmsalShenBeiaBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            Intent intent=new Intent();
            setResult(1, intent);
            finish();
        }else if (v.equals(qrBut)){
            XQRCode.startScan(Activity13b.this, REQUEST_CODE);
        }
    }
    //接收二维码返回结果，及页面返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理二维码扫描结果
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            IntentData = data;
            //处理扫描结果（在界面上显示）
            handleScanResult(IntentData);
        }else if (requestCode == 1 && data != null){
            Boolean isQuery =  data.getBooleanExtra("isQuery",false);
            if (isQuery){
                handleScanResult(IntentData);
            }
        }
    }
    //解析二维码结果,根据二维码结果加载巡检数据
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    //二维码返回结果
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    //对比扫描设备编号和页面点击设备编号是否一致
                    if (result.equals(ZZasmc_bean.getNumber())){
                        String uuid = ZZasmc_bean.getDataid();
                        GetSheBeibData(uuid);
                    }else {
                        String str = "扫描的设备编号和页面选择设备编号不一致，无法加载巡检数据";
                        String tag = "zzas01";
                        DialogFun(str,tag);
                    }
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    Toast.makeText(getApplicationContext(), "二维码扫描错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetSheBeibData(String uuid){
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
        rxjavaService .postMapSheBeib(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsalShenBeibBean>() {
                    @Override
                    public void onNext(wmsalShenBeibBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetSheBeibData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetSheBeibData(wmsalShenBeibBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<jwmsActivity13bModel> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsalShenBeibBean.outData item : ZZaswmsacBean.getOutData()) {
                jwmsActivity13bModel init = new jwmsActivity13bModel();
                init.setTask(item.getTask());
                init.setState(item.getState());
                lists.add(init);
            }
            jwmsActivity13bAdapter mAdapter = new jwmsActivity13bAdapter(Activity13b.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "当前没有巡检任务！", Toast.LENGTH_SHORT).show();
        }
    }
    //简单弹窗封装
    private void DialogFun(String str, String tag){
        zzasToolBdialog zzasdialog = new zzasToolBdialog();
        zzasdialog.showSimpleConfirmDialog(Activity13b.this,str,tag,null,null);
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(Activity13b.this,Activity13c.class);
        intent.putExtra("checkrecorda",ZZasmc_bean);
        intent.putExtra("checkrecordb",ZZaswmsacBean.getOutData()[position]);
        startActivityForResult(intent, 1);
    }

}
