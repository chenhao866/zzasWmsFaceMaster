/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.NonNull;
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
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.dwms.Beans.wmsakAdjustaBean;
import com.zzas.ewms.Tools.zzasToolBdialog;

import java.util.HashMap;
import java.util.Map;

public class Activity12b extends AppCompatActivity implements View.OnClickListener {
    private SuperButton reCheckBut;
    private SuperButton cancelBut;
    private SuperButton noCheckBut;
    private TextView productCode;
    private TextView productName;
    private TextView speciOne;
    private TextView wahoName;
    private TextView trayCodeNew;
    private TextView storCodeNew;
    private TextView sumNew;
    private wmsakAdjustaBean.outData ZZasmc_bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_12b);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        noCheckBut = findViewById(R.id.noCheckBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        noCheckBut.setOnClickListener(this);
        //textview
        productCode = findViewById(R.id.productCode);
        productName = findViewById(R.id.productName);
        speciOne = findViewById(R.id.speciOne);
        wahoName = findViewById(R.id.wahoName);
        trayCodeNew = findViewById(R.id.trayCodeNew);
        storCodeNew = findViewById(R.id.storCodeNew);
        sumNew = findViewById(R.id.sumNew);
        //页面传值初始化
        ZZasmc_bean = (wmsakAdjustaBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");//获取页面传递过来的商品数据
        SetShowPage();
    }
    //初始化页面显示
    private void SetShowPage(){
        productCode.setText(ZZasmc_bean.getProductcode());
        productName.setText(ZZasmc_bean.getProductname());
        speciOne.setText(ZZasmc_bean.getSpecione());
        wahoName.setText(ZZasmc_bean.getWahoname());
        trayCodeNew.setText(ZZasmc_bean.getTraycodenew());
        storCodeNew.setText(ZZasmc_bean.getStorcodenew());
        sumNew.setText(ZZasmc_bean.getSumnew());
    }
    // 按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//确认调整
            String json = JsonDataStrFun();
            AdjustaUpdateYes(json);
        }else if (v.equals(noCheckBut)){//放弃调整
            String tag = "noCheckBut";
            String str = "您确定要放弃库存调整吗？订单信息将会被返回原库位";
            DialogFun(str,tag);
        }else if (v.equals(cancelBut)){//取消
            Intent intent=new Intent();
            Boolean isQuery = false;
            intent.putExtra("isQuery", isQuery);
            setResult(2, intent);
            finish();
        }
    }
    //组装json数据
    private String JsonDataStrFun(){
        Gson gson = new Gson();
        Map<String,wmsakAdjustaBean.outData> map = new HashMap<>();
        map.put("setdata",ZZasmc_bean);
        String str = gson.toJson(map);
        return  str;
    }
    //执行确认库存调整请求接口
    protected void AdjustaUpdateYes(String setdata){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), setdata);//將json字符串转换为参数
        rxjavaService .postMapAdjustaUpdateY(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsajAdjustBean>() {
                    @Override
                    public void onNext(wmsajAdjustBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(), "库位调整完成", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            Boolean isQuery = true;
                            intent.putExtra("isQuery", isQuery);
                            setResult(2, intent);
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

    //执行放弃库存调整请求接口
    protected void AdjustaUpdateNo(String setdata){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), setdata);//將json字符串转换为参数
        rxjavaService .postMapAdjustaUpdateN(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsajAdjustBean>() {
                    @Override
                    public void onNext(wmsajAdjustBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(), "库位调整回复完成", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            Boolean isQuery = true;
                            intent.putExtra("isQuery", isQuery);
                            setResult(2, intent);
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
        zzasdialog.showSimpleConfirmDialog(Activity12b.this,str,tag,YesClick,null);
    }
    //弹窗确定按钮点击事件
    MaterialDialog.SingleButtonCallback YesClick = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if(dialog.getTag().equals("noCheckBut")){
                String json = JsonDataStrFun();
                AdjustaUpdateNo(json);
            }
        }
    };


}
