/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.iwmsActivity12Adapter;
import com.zzas.cwms.Adapter.iwmsActivity12Model;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.dwms.Beans.wmsakAdjustaBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Activity12 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageButton backBut;
    private ImageButton screenBut;
    private ListView lv_list;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private int REQUEST_CODE = 111;//用于二维码扫描
    private wmsajAdjustBean ZZaswmsacBean;//二维码扫描数据
    private wmsakAdjustaBean ZZaswmsacBeanTwo;//库位调整任务单数据
    private Intent IntentData;//二维码扫描结果
    private XUISimplePopup mMenuPopup;//弹出框菜单
    private AdapterItem[] menuItems = new AdapterItem[]{ //弹出选项显示的内容
            new AdapterItem("扫描库位", R.mipmap.saomiaobtn),
            new AdapterItem("调整任务",R.mipmap.diaokubut),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_12);
        backBut = findViewById(R.id.backBut);
        screenBut = findViewById(R.id.screenBut);
        lv_list = findViewById(R.id.lv_list);
        backBut.setOnClickListener(this);
        screenBut.setOnClickListener(this);
        lv_list.setOnItemClickListener(this);
        //初始化弹出选择
        initMenuPopup();
        //下拉刷新
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        swipeRefreshLayout.setOnRefreshListener(this);
        //显示调整任务信息
        String toporgid = zzasacUserMUO.getOrgTop();
        GetAdjustaData(toporgid);
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            finish();
        }else if (v.equals(screenBut)){//显示弹出选项
            mMenuPopup.showDown(v);
        }
    }
    //弹出选项事件
    private void initMenuPopup() {
        mMenuPopup = new XUISimplePopup(Activity12.this,menuItems)
                .create(new XUISimplePopup.OnPopupItemClickListener() {
                    @Override
                    public void onItemClick(XUISimpleAdapter adapter, AdapterItem item, int position) {
                        String str = item.getTitle().toString();
                        lv_list.setAdapter(null);//获取数据前情况列表信息
                        if (str.equals("扫描库位")){
                            XQRCode.startScan(Activity12.this, REQUEST_CODE);
                        }else if (str.equals("调整任务")){
                            String toporgid = zzasacUserMUO.getOrgTop();
                            GetAdjustaData(toporgid);
                        }
                    }
                });
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
        }else if(resultCode == 1){
            Boolean isQuery =  data.getBooleanExtra("isQuery",false);
            if (isQuery){
                handleScanResult(IntentData);
            }
        }else if(resultCode == 2){
            Boolean isQuery =  data.getBooleanExtra("isQuery",false);
            if (isQuery){
                String toporgid = zzasacUserMUO.getOrgTop();
                GetAdjustaData(toporgid);
            }
        }
    }
    //解析二维码结果
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    //对返回结果进行解密操作
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    Uri uri = Uri.parse(result);
                    String key= uri.getQueryParameter("key");
                    String storcode = new String(Base64.decode(key.getBytes(), Base64.DEFAULT));
                    //请求数据
                    String toporgid = zzasacUserMUO.getOrgTop();
                    GetAdjustData(toporgid,storcode);
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    Toast.makeText(getApplicationContext(), "二维码扫描错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //执行获得库位调整商品信息接口
    protected void GetAdjustData(String toporgid,String  storcode){
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
        myMap.put("storcode",storcode);
        rxjavaService .postMapAdjust(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsajAdjustBean>() {
                    @Override
                    public void onNext(wmsajAdjustBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetAdjustData(weatherEntity);//根据返回数据显示下拉信息
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
    private void SetAdjustData(wmsajAdjustBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<iwmsActivity12Model> lists = new ArrayList<>();
            ZZaswmsacBean = weatherEntity;
            for (wmsajAdjustBean.outData item : ZZaswmsacBean.getOutData()) {
                iwmsActivity12Model init = new iwmsActivity12Model();
                init.setProductName(item.getProductname());
                init.setSum(item.getTmpsum());
                init.setTag("扫描库位");
                lists.add(init);
            }
            iwmsActivity12Adapter mAdapter = new iwmsActivity12Adapter(Activity12.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "库位没有商品！", Toast.LENGTH_SHORT).show();
        }
    }

    //执行获得库位调整任务单信息接口
    protected void GetAdjustaData(String toporgid){
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
        rxjavaService .postMapAdjusta(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsakAdjustaBean>() {
                    @Override
                    public void onNext(wmsakAdjustaBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetAdjustaData(weatherEntity);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        HideXiaLa();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                        HideXiaLa();
                    }
                });
    }
    //根据接口数据显示listView
    private void SetAdjustaData(wmsakAdjustaBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<iwmsActivity12Model> lists = new ArrayList<>();
            ZZaswmsacBeanTwo = weatherEntity;
            for (wmsakAdjustaBean.outData item : ZZaswmsacBeanTwo.getOutData()) {
                iwmsActivity12Model init = new iwmsActivity12Model();
                init.setProductName(item.getProductname());
                init.setSum(item.getSumnew());
                init.setTag("调整任务");
                lists.add(init);
            }
            iwmsActivity12Adapter mAdapter = new iwmsActivity12Adapter(Activity12.this,lists);
            lv_list.setAdapter(mAdapter);
        }else {
            lv_list.setAdapter(null);
            Toast.makeText(getApplicationContext(), "没有库位调整任务信息", Toast.LENGTH_SHORT).show();
        }
    }
    //listview点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = view.findViewById(R.id.productName);//通过view找到点击对应行的控件
        String str = tv.getTag().toString();
        if(str.equals("扫描库位")){//跳转到调库订单下达页面
            Intent intent=new Intent(Activity12.this,Activity12a.class);
            intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBean.getOutData()[position]);
            startActivityForResult(intent, 1);
        }else if (str.equals("调整任务")){
            Intent intent=new Intent(Activity12.this,Activity12b.class);
            intent.putExtra("ZZaswmsacBeanItem",ZZaswmsacBeanTwo.getOutData()[position]);
            startActivityForResult(intent, 2);
        }
    }
    //下拉刷新事件
    @Override
    public void onRefresh() {
        String toporgid = zzasacUserMUO.getOrgTop();
        GetAdjustaData(toporgid);
    }
    //隐藏下拉加载
    private void HideXiaLa(){
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
