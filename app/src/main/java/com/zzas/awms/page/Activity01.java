/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.fl.DetectLoginActivity;

import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasaawmsMuo;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.aclass.zzasaeLogin;
import com.zzas.awms.wmsapplication.R;
import com.zzas.bwms.kyloading.KyLoadingBuilder;
import com.zzas.fwms.Xupdate.APKVersionCodeUtils;
import com.zzas.fwms.Xupdate.OKHttpUpdateHttpService;


import java.util.HashMap;
import java.util.Map;


import androidx.core.app.ActivityCompat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

public class Activity01 extends AppCompatActivity {
    private ImageButton FaceBut;
    private EditText userName;
    private EditText passWord;
    private Button loginBut;
    private CheckBox JiZhuCkb;
    private  Boolean  isCheckedBox;
    private TextView versionsTV;
    private KyLoadingBuilder loadingZZAS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        FaceBut = findViewById(R.id.faceBut);
        loginBut = findViewById(R.id.loginBut);
        JiZhuCkb = findViewById(R.id.JiZhuCkb);
        versionsTV = findViewById(R.id.versionsTV);
        SetEditData();//根据记住密码状态，初始化输入框
        //初始化版本信息显示
        ShowVersionsFun();
        //添加复选框事件
        setChackBoxChange(JiZhuCkb);
        //添加输入框事件
        setEditTextInputSpace(userName);
        setEditTextInputSpace(passWord);
        //添加按钮事件
        PlayClickListenerZZAS playClickListen = new PlayClickListenerZZAS();
        FaceBut.setOnClickListener(playClickListen);
        loginBut.setOnClickListener(playClickListen);//添加权限
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },1);
        //版本更新
        initUpdate();
        updateInitFun();
    }

    //登录事件
    private void LogonFun(){
        if (verifyFun()){//账号密码验证输入
            String username = userName.getText().toString();
            String password = passWord.getText().toString();
            //根据账号密码执行登录
            Login( username, password);
        }
    }
    //验证输入框不能为空
    private Boolean verifyFun(){
        String  textuser = String.valueOf(userName.getText());
        String  textpass = String.valueOf(passWord.getText());
        if(textuser.equals("") || textpass.equals("")){
            Toast.makeText(getApplicationContext(), "账户或密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //根据记住密码状态，初始化输入框
    private void SetEditData() {
        //获取存储的信息
        Map map = GetHomeData();
        String  username = map.get("userName").toString();
        String  password = map.get("passWord").toString();
        Object ckbOff= map.get("ckbOff");
        if(ckbOff.equals(false)){
            JiZhuCkb.setChecked(false);
            isCheckedBox = false;
        }else {
            JiZhuCkb.setChecked(true);
            userName.setText(username);
            passWord.setText(password);
            isCheckedBox = true;
        }
    }
    //获取账号密码
    private Map GetHomeData() {
        //创建一个名称HomeData的数据文件,如果没有就创建
        SharedPreferences sharedPreferences = getSharedPreferences("HomeData", MODE_PRIVATE);
        //获取键值对信息,如果有就获取对应值，如果没有就获得括号中的值
        String username = sharedPreferences.getString("userName","");
        String password = sharedPreferences.getString("passWord","");
        Boolean ckbOff = sharedPreferences.getBoolean("ckbOff",false);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("passWord",password);
        map.put("ckbOff", ckbOff);
        return map;
    }
    //记住账号密码
    private void SetHomeData(String username,String password,Boolean ckbOff) {
        //创建一个名称HomeData的数据文件,如果没有就创建
        SharedPreferences sharedPreferences = getSharedPreferences("HomeData", MODE_PRIVATE);
        //使文件处理编辑状态
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //向文件中存放数据
        editor.putString("userName", username);
        editor.putString("passWord",password);
        editor.putBoolean("ckbOff", ckbOff);
        //提交数据
        editor.commit();
    }
    //点击事件类
    class PlayClickListenerZZAS implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.equals(FaceBut)) {
                //刷脸登录跳转到刷脸页面
                Intent intent=new Intent(Activity01.this,DetectLoginActivity.class);
                startActivity(intent);
            }else if(v.equals(loginBut)){
                //按钮点击登录
                LogonFun();
            }
        }
    }
    //禁止输入框输入空格和换行
    public static void setEditTextInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().contains(" ") || source.toString().contains("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
    //监听记住密码状态
    public void setChackBoxChange(CheckBox checkBox){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedBox = isChecked;
            }
        });
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
                            //设置记住密码
                            String username = userName.getText().toString();
                            String password = passWord.getText().toString();
                            SetHomeData(username,password,isCheckedBox);
                            //跳转页面
                            Intent intent=new Intent(Activity01.this,Activity02.class);
                            startActivity(intent);
                            finish();
                        }else if(RetCode == -3){
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
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        //zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                    }
                });
    }
    //检验版本更新
    private void updateInitFun(){
        int versionCode = APKVersionCodeUtils.getVersionCode(getApplicationContext());//获取当前版本号
        String mUpdateUrl = new Appcofig().GetCofigData(Activity01.this,"APKUpdate");//获取配置
        XUpdate.newBuild(Activity01.this)
                .updateUrl(mUpdateUrl)
                .param("versioncode",versionCode)
                .supportBackgroundUpdate(true)
                .update();
    }
    //版本更新配置
    private void initUpdate() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(this))  //设置默认公共请求参数
                .param("appKey", getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() { //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                           // Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(getApplication());                                          //这个必须初始化
    }
    //初始化版本信息显示
    private void ShowVersionsFun(){
        int versionCode = APKVersionCodeUtils.getVersionCode(getApplicationContext());//获取当前版本号
        String str = "V:"+versionCode+".0.0";
        versionsTV.setText(str);
    }

}
