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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.dwms.Beans.wmsalShenBeiaBean;
import com.zzas.dwms.Beans.wmsalShenBeibBean;

import java.util.HashMap;
import java.util.Map;

public class Activity13c extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private SuperButton reCheckBut;
    private SuperButton cancelBut;
    private TextView task;
    private RadioGroup state;
    private RadioButton radiobutton1;
    private RadioButton radiobutton2;
    private MultiLineEditText text;
    private wmsalShenBeiaBean.outData checkrecorda;
    private wmsalShenBeibBean.outData checkrecordb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_13c);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        //textView
        task = findViewById(R.id.task);
        //单选框
        state = findViewById(R.id.state);
        state.setOnCheckedChangeListener(this);
        radiobutton1 = findViewById(R.id.radiobutton1);
        radiobutton2 = findViewById(R.id.radiobutton2);
        //多文本输入
        text = findViewById(R.id.text);
        //页面传值
        checkrecorda = (wmsalShenBeiaBean.outData) getIntent().getSerializableExtra("checkrecorda");
        checkrecordb = (wmsalShenBeibBean.outData) getIntent().getSerializableExtra("checkrecordb");

        SetPageShowFun();
    }
    //初始化页面显示
    private void SetPageShowFun(){
        task.setText(checkrecordb.getTask());
        if(checkrecordb.getState().equals("2")){
            state.check(radiobutton2.getId());
        }else {
            state.check(radiobutton1.getId());
            checkrecordb.setState("1");
        }
        text.setContentText(checkrecordb.getText());
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//执行确认巡检
            String json = JsonDataStr();
            UpdateSheBeiData(json);
            Log.e("zzz",json);

        }else if (v.equals(cancelBut)){//取消
            Intent intent=new Intent();
            Boolean isQuery = false;
            intent.putExtra("isQuery", isQuery);
            setResult(1, intent);
            finish();
        }
    }
    //组装json字符串
    private String JsonDataStr() {
        checkrecordb.setText(text.getContentText());
        Map<String,Object> map = new HashMap<>();
        checkrecorda.setEmpid(zzasacUserMUO.getUserId());
        checkrecorda.setEmpname(zzasacUserMUO.getUserRealName());
        map.put("checkrecorda",checkrecorda);
        map.put("checkrecordb",checkrecordb);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        return str;
    }
    //执行设备巡检确认请求接口
    protected void UpdateSheBeiData(String setdata){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), setdata);//將json字符串转换为参数
        rxjavaService .postMapSheBiebeiUpdate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsajAdjustBean>() {
                    @Override
                    public void onNext(wmsajAdjustBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(), "巡检提交成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            Boolean isQuery = true;
                            intent.putExtra("isQuery", isQuery);
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
    //单选框点击事件
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == radiobutton1.getId()){
            checkrecordb.setState("1");
        }else if(checkedId == radiobutton2.getId()){
            checkrecordb.setState("2");
        }
    }
}
