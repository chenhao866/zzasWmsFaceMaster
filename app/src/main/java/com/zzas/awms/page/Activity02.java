/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.fl.MainActivity;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.proxy.IUpdateParser;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.aclass.zzasafMenu;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.awmsHomeAdapter;
import com.zzas.cwms.Adapter.awmsHomeModel;
import com.zzas.ewms.Tools.zzasToolCJsonMap;
import com.zzas.fwms.Xupdate.APKVersionCodeUtils;
import com.zzas.fwms.Xupdate.OKHttpUpdateHttpService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;
import static java.lang.Thread.sleep;

public class Activity02 extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageButton QRcodeBut;
    private ImageButton voiceBut;
    private awmsHomeAdapter mAdapter;
    private ListView lv_list;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private XUISimplePopup mMenuPopup;//扫描弹出框菜单
    private XUISimplePopup mMenuPopupGN;//功能弹出框菜单
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.init(getApplication()); //初始化UI框架
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02);
        //按钮
        QRcodeBut = findViewById(R.id.QRcodeBut);
        QRcodeBut.setOnClickListener(this);
        voiceBut = findViewById(R.id.voiceBut);
        voiceBut.setOnClickListener(this);
        //listview
        lv_list=findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(this);
        //下拉刷新
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        swipeRefreshLayout.setOnRefreshListener(this);
        //获取角色信息
        String RoleList =zzasacUserMUO.getRoleList();
        JurisdictionFun(RoleList);
        //扫描弹出列表
        initMenuPopup();
        //功能弹出列表
        initMenuPopupGN();
        //初始化版本更新
        initUpdate();
    }
    //扫描弹出框显示的内容
    private AdapterItem[] ShowDataListFun(){
        //弹出框需要显示的内容
       AdapterItem[] menuItems = new AdapterItem[]{
                new AdapterItem("库位", R.mipmap.cangku),
                new AdapterItem("设备",R.mipmap.shebei),
                new AdapterItem("制度",R.mipmap.chufenzhiduchakan)
        };
       return menuItems;
    }
    //功能弹出框显示的内容
    private AdapterItem[] ShowDataListFunGN(){
        //弹出框需要显示的内容
        AdapterItem[] menuItems = new AdapterItem[]{
                new AdapterItem("智能语音", R.mipmap.yuyinzzas),
                new AdapterItem("人脸注册",R.mipmap.xiaoliansmileyzzas),
                new AdapterItem("版本更新",R.mipmap.zzasupdate)
        };
        return menuItems;
    }
    //执行获取权限
    protected void JurisdictionFun(String RoleList){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("roleList",RoleList);
        rxjavaService .postMapJurisdiction(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<zzasafMenu>() {
                    @Override
                    public void onNext(zzasafMenu weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            ShowAdapter(weatherEntity);//根据返回数据显示按钮信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        //停止下拉刷新
                        HideXiaLa();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行.
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                        //停止下拉刷新
                        HideXiaLa();
                    }
                });
    }
    //根据返回数据显示对应的权限按钮
    private  void ShowAdapter(zzasafMenu zzasaf_menu){
        int sum = zzasaf_menu.getOutData().length;
        if(sum > 0){
            List<awmsHomeModel> zzasModel = new ArrayList<>();
            int colorSum[] = {R.drawable.zzas_ac_style,R.drawable.zzas_aca_style,R.drawable.zzas_acb_style,
                    R.drawable.zzas_acd_style,R.drawable.zzas_ace_style};
            for (zzasafMenu.outData item : zzasaf_menu.getOutData()) {
                awmsHomeModel awms_HomeModel = new awmsHomeModel();
                    awms_HomeModel.setListLayout(colorSum[item.getChilnode()]);
                    awms_HomeModel.setLiatImageView(getResource(item.getClassico()));
                    awms_HomeModel.setListTextViem(item.getTextzh());
                    zzasModel.add(awms_HomeModel);
            }
            mAdapter = new awmsHomeAdapter(Activity02.this,zzasModel);
            lv_list.setAdapter(mAdapter);
        }else {
            String str = "当前账号没有权限！请指定权限后重新登录";
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }

    }
    //根据图片名称获取图片id
    public int  getResource(String imageName) {
        Context ctx = getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        return resId;
    }
    //listView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = view.findViewById(R.id.listTextViem);//通过view找到点击对应行的控件
        String str = tv.getText().toString();
        itemFun( str);//根据所点击的listView执行对应的方法
    }
    //根据所点击的listView执行对应的方法
    private void itemFun( String keyName){
        switch (keyName){
            case "到货复核":
                Intent intent01 = new Intent(Activity02.this,Activity03.class);
                startActivity(intent01);
                break;
            case "入库上架":
                Intent intent02 = new Intent(Activity02.this,Activity06.class);
                startActivity(intent02);
                break;
            case "拣货确认":
                Intent intent03 = new Intent(Activity02.this,Activity09.class);
                startActivity(intent03);
                break;
            case "出库复核":
                Intent intent04 = new Intent(Activity02.this,Activity10.class);
                startActivity(intent04);
                break;
            case "库存盘点":
                Intent intent05 = new Intent(Activity02.this,Activity11.class);
                startActivity(intent05);
                break;
            case "库位调整":
                Intent intent06 = new Intent(Activity02.this,Activity12.class);
                startActivity(intent06);
                break;
            case "设备巡检":
                Intent intent07 = new Intent(Activity02.this,Activity13.class);
                startActivity(intent07);;
                break;
            case "安全巡检":
                Intent intent08 = new Intent(Activity02.this,Activity14.class);
                startActivity(intent08);
                break;
            case "派送管理":
                Intent intent09 = new Intent(Activity02.this,Activity15.class);
                startActivity(intent09);
                break;
            case "运输管理":
                Intent intent10 = new Intent(Activity02.this,Activity16.class);
                startActivity(intent10);
                break;
            default:
                Toast.makeText(getApplicationContext(), "执行错误，请联系管理员！", Toast.LENGTH_SHORT).show();
                break;

        }

    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(QRcodeBut)){ //v为弹出框所要显示附近的控件
            mMenuPopup.showDown(v);
        }if (v.equals(voiceBut)){//智能语音和人脸注册
            mMenuPopupGN.showDown(v);
        }
    }
    //扫描弹出框点击事件，需要使用前提前加载
    private void initMenuPopup() {
        mMenuPopup = new XUISimplePopup(Activity02.this,ShowDataListFun())
                .create(new XUISimplePopup.OnPopupItemClickListener() {
                    @Override
                    public void onItemClick(XUISimpleAdapter adapter, AdapterItem item, int position) {
                        String str = item.getTitle().toString();
                        if (str.equals("库位")){
                            XQRCode.startScan(Activity02.this, 100);
                        }else if (str.equals("设备")){
                            XQRCode.startScan(Activity02.this, 200);
                        }else if (str.equals("制度")){
                            XQRCode.startScan(Activity02.this, 300);
                        }
                    }
                });
    }
    //功能弹出框点击事件，需要使用前提前加载
    private void initMenuPopupGN() {
        mMenuPopupGN = new XUISimplePopup(Activity02.this,ShowDataListFunGN())
                .create(new XUISimplePopup.OnPopupItemClickListener() {
                    @Override
                    public void onItemClick(XUISimpleAdapter adapter, AdapterItem item, int position) {
                        String str = item.getTitle().toString();
                        if (str.equals("智能语音")){
                            Intent intent=new Intent(Activity02.this,Activity19.class);
                            startActivity(intent);
                        }else if (str.equals("人脸注册")){
                            Intent intent=new Intent(Activity02.this,Activity18.class);
                            startActivity(intent);
                        }else if (str.equals("版本更新")){
                            int versionCode = APKVersionCodeUtils.getVersionCode(getApplicationContext());//获取当前版本号
                            String mUpdateUrl = new Appcofig().GetCofigData(Activity02.this,"APKUpdate");//获取配置
                            XUpdate.newBuild(Activity02.this)
                                    .updateUrl(mUpdateUrl)
                                    .param("versioncode",versionCode)
                                    .supportBackgroundUpdate(true)
                                    .update();
                        }
                    }
                });
    }
    //接收二维码返回结果，及页面返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理二维码扫描结果
        if (requestCode == 100 && resultCode == RESULT_OK) {//如果扫描库位
            //处理扫描结果（在界面上显示）
            handleScanResultKuWei(data);
        }else if(requestCode == 200 && resultCode == RESULT_OK){//如果扫描的是设备
            handleScanResultSheBei(data);
        }else if(requestCode == 300 && resultCode == RESULT_OK){//如果扫描的是制度
            handleScanResultZhiDu(data);
        }
    }
    //解析库位二维码结果
    private void handleScanResultKuWei(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    //对返回结果进行解密操作
                    try {
                        String result = bundle.getString(XQRCode.RESULT_DATA);
                        Uri uri = Uri.parse(result);
                        String key= uri.getQueryParameter("key");
                        String storcode = new String(Base64.decode(key.getBytes(), Base64.DEFAULT));
                        Intent intent=new Intent(Activity02.this,Activity17a.class);
                        intent.putExtra("storcode", storcode);
                        startActivity(intent);
                    }catch (Throwable e){
                        Toast.makeText(getApplicationContext(), "扫描库位错误，请扫描正确的库位！", Toast.LENGTH_SHORT).show();
                    }
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    Toast.makeText(getApplicationContext(), "二维码扫描错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //解析设备二维码结果
    private void handleScanResultSheBei(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    //对返回结果进行解密操作
                    try {

                        String result = bundle.getString(XQRCode.RESULT_DATA);
                        Intent intent=new Intent(Activity02.this,Activity17b.class);
                        intent.putExtra("number", result);
                        if (result.indexOf("http://") >= 0){
                            Uri uri = Uri.parse(result);
                            String key= uri.getQueryParameter("key");
                            intent.putExtra("number", key);
                        }
                        startActivity(intent);
                    }catch (Throwable e){
                        Toast.makeText(getApplicationContext(), "扫描设备错误，请扫描正确的设备！", Toast.LENGTH_SHORT).show();
                    }
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    Toast.makeText(getApplicationContext(), "二维码扫描错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //解析制度二维码结果
    private void handleScanResultZhiDu(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    //对返回结果进行解密操作
                    try {
                        String result = bundle.getString(XQRCode.RESULT_DATA);
                        Uri uri = Uri.parse(result);
                        String key= uri.getQueryParameter("key");
                        if (isInteger(key)){
                            Intent intent=new Intent(Activity02.this,Activity17c.class);
                            intent.putExtra("qruuid", key);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "扫描制度错误，请扫描正确的二维码！", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Throwable e){
                        Toast.makeText(getApplicationContext(), "扫描制度错误，请扫描正确的二维码！", Toast.LENGTH_SHORT).show();
                    }
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    Toast.makeText(getApplicationContext(), "二维码扫描错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //判断字符串是否为整数,用于判断扫描的是否制度二维码
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
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
                            if (error.getCode() == 2006){
                                Toast.makeText(getApplicationContext(), "已是最新版本", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(getApplication());                                          //这个必须初始化
    }
    //下拉刷新事件
    @Override
    public void onRefresh() {
        String RoleList =zzasacUserMUO.getRoleList();
        JurisdictionFun(RoleList);
    }
    //隐藏下拉加载
    private void HideXiaLa(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


}
