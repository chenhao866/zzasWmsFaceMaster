/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.NonNull;
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

import com.baidu.aip.fl.MainActivity;
import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.aclass.zzasagKeyValueInfo;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.bwmsActivity03Adapter;
import com.zzas.cwms.Adapter.bwmsActivity03Model;
import com.zzas.cwms.Adapter.cwmsActivity04Adapter;
import com.zzas.cwms.Adapter.cwmsActivity04Model;
import com.zzas.dwms.Beans.wmsacEnterlistBean;
import com.zzas.dwms.Beans.wmsadEntercomlistBean;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.ewms.Tools.zzasToolBdialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity04 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageButton backBut;
    private Button SaveBut;
    private ListView lv_list;
    private wmsadEntercomlistBean ZZasEntercomlistBean;
    private wmsacEnterlistBean.outData ZZasmc_bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_04);
        backBut = findViewById(R.id.backBut);
        SaveBut = findViewById(R.id.SaveBut);
        lv_list = findViewById(R.id.lv_list);
        backBut.setOnClickListener(this);
        SaveBut.setOnClickListener(this);
        //请求数据初始化页面显示
        ZZasmc_bean = (wmsacEnterlistBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");//获取页面传递过来的入库清单数据
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        GetEnterlistData(topOrg,ZZasmc_bean.getEntersum());
        lv_list.setOnItemClickListener(this);
    }
    //按钮事件
    @Override
    public void onClick(View v) {
        if(v.equals(backBut)){
            verifyRecheckFun();//验证数据是否存在已复核的数据，询问是否退出
        }else if(v.equals(SaveBut)){
            Boolean IsFuHe = NoVerifyRecheckFun();//保存时验证是否所有商品都已复核
            if(IsFuHe){
                String jsonSrt = packageJsonDataFun();
                SetStoragetData(jsonSrt);
            }
        }
    }
    //执行获得入库清单详情信息接口
    protected void GetEnterlistData(String topOrg,String  enterSum){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("toporgid",topOrg);
        myMap.put("entersum",enterSum);
        rxjavaService .postMapEntercomlist(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsadEntercomlistBean>() {
                    @Override
                    public void onNext(wmsadEntercomlistBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetEntercomlistData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetEntercomlistData(wmsadEntercomlistBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            ZZasEntercomlistBean = weatherEntity;
            List<cwmsActivity04Model> lists = new ArrayList<>();
            for (wmsadEntercomlistBean.outData item : weatherEntity.getOutData()) {
                cwmsActivity04Model init = new cwmsActivity04Model();
                init.setEnterSum(item.getEntersum());
                init.setCilentName(item.getCilentname());
                init.setProductCode(item.getProductcode());
                init.setProductName(item.getProductname());
                init.setPredictSum(item.getPredictsum()+"");
                init.setPredictTime(item.getPredicttime());
                init.setDifference(item.getDifference());
                init.setState(item.getState()+"");
                lists.add(init);
            }
            cwmsActivity04Adapter mAdapter = new cwmsActivity04Adapter(Activity04.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            Toast.makeText(getApplicationContext(), "没有入库清单详情信息", Toast.LENGTH_SHORT).show();
        }
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(Activity04.this,Activity05.class);
        intent.putExtra("BeanData", ZZasEntercomlistBean);
        intent.putExtra("position", position);
        startActivityForResult(intent, 1);
    }
    //跨页面返回回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            Boolean isQuery = data.getBooleanExtra("isQuery",false);
            if(isQuery){
                wmsadEntercomlistBean mc_bean= (wmsadEntercomlistBean)data.getSerializableExtra("BeanData");
                SetEntercomlistData(mc_bean);//将返回信息重新加载listView
            }
        }
    }
    //验证数据是否存在已复核的数据，询问是否退出
    private void verifyRecheckFun(){
        if(ZZasEntercomlistBean == null) finish();
            for (wmsadEntercomlistBean.outData item : ZZasEntercomlistBean.getOutData()) {
                if(item.getState() == 2){
                    DialogFun("有已复核的数据，确定放弃复核订单吗?", "backBut");
                    return ;
                }
            }
        finish();
    }
    //验证数据是否存在未复核的数据，对数据进行入库处理
    private Boolean NoVerifyRecheckFun(){
        if(ZZasEntercomlistBean == null || ZZasEntercomlistBean.getOutData().length <= 0){
            Toast.makeText(getApplicationContext(), "没有要复核的数据", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (wmsadEntercomlistBean.outData item : ZZasEntercomlistBean.getOutData()) {
            if(item.getState() == 0){
                String str = "有未复核的商品，请复核完成后再保存复核结果。";
                String tag = "zzasb";
                zzasToolBdialog zzasdialog = new zzasToolBdialog();
                zzasdialog.showSimpleTipDialog(Activity04.this,str,tag,YesClick);
                return false;
            }
        }
        return true;
    }
    //入库时将参数数据封装为json字符串
    private String packageJsonDataFun(){
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        String creaname = zzasacUserMUO.getUserRealName();
        Map<String,Object> map = new HashMap<>();
        map.put("topOrg",topOrg);
        map.put("creaname",creaname);
        map.put("ykData",ZZasmc_bean);
        map.put("setdata",ZZasEntercomlistBean.getOutData());
        Gson gson = new Gson();
        String str = gson.toJson(map);
        return  str;
    }
    //执行入库请求接口
    protected void SetStoragetData(String JsonData){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonData);//將json字符串转换为参数
        rxjavaService .postMapSetStorage(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsafStolocBean>() {
                    @Override
                    public void onNext(wmsafStolocBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(), "订单复核成功", Toast.LENGTH_SHORT).show();
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






    //简单弹窗封装
    private void DialogFun(String str, String tag){
        zzasToolBdialog zzasdialog = new zzasToolBdialog();
        zzasdialog.showSimpleConfirmDialog(Activity04.this,str,tag,YesClick,null);
    }
    //弹窗确定按钮点击事件
    MaterialDialog.SingleButtonCallback YesClick = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if(dialog.getTag().equals("backBut")){
                finish();
            }
        }
    };








}
