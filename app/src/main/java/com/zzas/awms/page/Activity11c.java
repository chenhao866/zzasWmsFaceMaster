/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsadEntercomlistBean;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsaBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsbBean;

import java.util.HashMap;
import java.util.Map;

public class Activity11c extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private SuperButton reCheckBut;
    private SuperButton cancelBut;
    private MultiLineEditText text;
    private MaterialEditText productCode;
    private MaterialEditText productName;
    private MaterialEditText speciOne;
    private MaterialEditText tmpsum;
    private MaterialEditText sumcount;
    private RadioGroup state;
    private RadioButton radiobutton1;
    private RadioButton radiobutton2;
    private wmsaiInventoryrecordsbBean.outData ZZasmc_bean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11c);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        //多文本
        text = findViewById(R.id.text);
        //文本框
        productCode = findViewById(R.id.productCode);
        productName = findViewById(R.id.productName);
        speciOne = findViewById(R.id.speciOne);
        tmpsum = findViewById(R.id.tmpsum);
        sumcount = findViewById(R.id.sumcount);
        //单选框
        state =findViewById(R.id.state);
        radiobutton1 = findViewById(R.id.radiobutton1);
        radiobutton2 = findViewById(R.id.radiobutton2);
        state.setOnCheckedChangeListener(this);

        ZZasmc_bean = (wmsaiInventoryrecordsbBean.outData) getIntent().getSerializableExtra("BeanData");//获取页面传递过来的盘点库位数据
        SetPageData();
    }
    //初始化页面显示
    private void SetPageData(){
        productCode.setText(ZZasmc_bean.getProductcode());
        productName.setText(ZZasmc_bean.getProductname());
        speciOne.setText(ZZasmc_bean.getSpecione());
        tmpsum.setText(ZZasmc_bean.getTmpsum());
        sumcount.setText(ZZasmc_bean.getSumcount());
        text.setContentText(ZZasmc_bean.getText());
        if(ZZasmc_bean.getState().equals("2")){
            state.check(radiobutton2.getId());
            ZZasmc_bean.setState("2");
        }else {
            state.check(radiobutton1.getId());
            ZZasmc_bean.setState("3");
        }
    }

    //按钮事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){
            //确认操作
            if(sumcount.validate()){//验证盘点数量
                ZZasmc_bean.setText(text.getContentText());
                ZZasmc_bean.setSumcount(sumcount.getEditValue());
                Gson gson = new Gson();
                Map<String, wmsaiInventoryrecordsbBean.outData> sfsa = new HashMap<>();
                sfsa.put("setdata",ZZasmc_bean);
                String str = gson.toJson(sfsa);
                UpdateInventoryrecordsData(str);
            }
        }else if(v.equals(cancelBut)){
            //取消操作
            Intent intent=new Intent();
            setResult(1, intent);
            finish();
        }

    }
    //执行盘点请求接口
    protected void UpdateInventoryrecordsData(String JsonData){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonData);//將json字符串转换为参数
        rxjavaService .postMapSetPanDian(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsafStolocBean>() {
                    @Override
                    public void onNext(wmsafStolocBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(), "本条盘点提交成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.putExtra("isQuery", true);
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
    //单选框事件
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == radiobutton1.getId()){
            ZZasmc_bean.setState("3");
        }else if(checkedId == radiobutton2.getId()){
            ZZasmc_bean.setState("2");
        }

    }
}
