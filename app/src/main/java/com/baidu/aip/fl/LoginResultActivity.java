/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.aip.fl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.aip.fl.utils.ImageSaveUtil;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasaawmsMuo;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.aclass.zzasaeLogin;
import com.zzas.awms.page.Activity02;
import com.zzas.awms.wmsapplication.R;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 登陆结果页面
 */

public class LoginResultActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 100;
    private TextView resultTv;
    private TextView uidTv;
    private TextView scoreTv;
    private Button backBtn;
    private ImageView headIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_result);

        findView();
        addListener();
        displayData();

    }

    private void findView() {
        resultTv = (TextView) findViewById(R.id.result_tv);
        uidTv = (TextView) findViewById(R.id.uid_tv);
        scoreTv = (TextView) findViewById(R.id.score_tv);
        backBtn = (Button) findViewById(R.id.back_btn);
        headIv = (ImageView) findViewById(R.id.head_iv);
    }

    private void displayData() {
        Intent intent = getIntent();
        if (intent != null) {
            boolean loginSuccess = intent.getBooleanExtra("login_success", false);

            if (loginSuccess) {
                resultTv.setText("识别成功");
                String uid = intent.getStringExtra("uid");
                String userInfo = intent.getStringExtra("user_info");
                double score = intent.getDoubleExtra("score", 0);
                if (TextUtils.isEmpty(userInfo)) {
                    uidTv.setText(uid);
                } else {
                    uidTv.setText(userInfo);
                }
                scoreTv.setText(String.valueOf(score));

                String[] strArr = userInfo.split(",");
                if(strArr.length == 2 ){
                    String username = strArr[0];
                    String password = strArr[1];
                    Login( username, password);
                }else {
                    Toast.makeText(getApplicationContext(), "人脸与账号不匹配", Toast.LENGTH_SHORT).show();
                    finish();

                }
            } else {
                resultTv.setText("识别失败");
                String uid = intent.getStringExtra("uid");
                String errorMsg = intent.getStringExtra("error_msg");
                uidTv.setText(uid);
                scoreTv.setText(String.valueOf(errorMsg));
                Toast.makeText(getApplicationContext(), "人脸识别错误！", Toast.LENGTH_SHORT).show();
                finish();
            }
            headIv.setVisibility(View.VISIBLE);
            Bitmap bmp = ImageSaveUtil.loadCameraBitmap(this, "head_tmp.jpg");
            if (bmp != null) {
                headIv.setImageBitmap(bmp);
            }

        }

    }

    private void addListener() {
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (backBtn == v) {
            finish();
        }
    }

    //执行登录接口
    protected void Login(final String username, final String password){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("userId",username);
        myMap.put("password",password);
        rxjavaService .postMapLogin(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<zzasaawmsMuo>() {
                    @Override
                    public void onNext(zzasaawmsMuo weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        zzasadLoad.ShowZZASLoad(null,false);
                        int RetCode = weatherEntity.getRetCode();
                        if(RetCode == 1){//如果账号验证成功
                            zzasaeLogin zzasLogin = new zzasaeLogin();
                            zzasLogin.globalFun(weatherEntity);//muo保存为全局

                            //跳转页面
                            Intent intent=new Intent(LoginResultActivity.this, Activity02.class);
                            startActivity(intent);
                            finish();
                        }else if (RetCode == -3){
                            Login( username, password);
                        }else {
                            zzasaeLogin zzasLogin = new zzasaeLogin();
                            String str =  zzasLogin.statusFun(RetCode);
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                       zzasadLoad.ShowZZASLoad(null,false);
                       Toast.makeText(getApplicationContext(), "登录接口请求错误！", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                       // zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                    }
                });
    }

}
