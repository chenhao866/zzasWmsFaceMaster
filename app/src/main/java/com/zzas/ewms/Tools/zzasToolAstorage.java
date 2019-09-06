/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * 仓库工具只用于下拉框
 */
package com.zzas.ewms.Tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinnerAdapter;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasadLoad;
import com.zzas.aclass.zzasagKeyValueInfo;
import com.zzas.awms.page.Activity08;
import com.zzas.dwms.Beans.wmsabwahoBean;
import com.zzas.dwms.Beans.wmsadEntercomlistBean;
import com.zzas.dwms.Beans.wmsaeTrayBean;
import com.zzas.dwms.Beans.wmsafStolocBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class zzasToolAstorage {

    /***
     * 用于获取仓库下拉框数据，并将数据指定给下拉框
     * @param context 当前页面的上下文
     * @param spinner 需要指定数据的下拉框
     * @param topOrg  用于查询数据的机构id
     */
    public void GetWahoDataTool(final Context context,final MaterialSpinner spinner,String topOrg){
        zzasadLoad.ShowZZASLoad(context,true);//加载框
        String url = new Appcofig().GetCofigData(context,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("toporgid",topOrg);
        rxjavaService .postMapWaho(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsabwahoBean>() {
                    @Override
                    public void onNext(wmsabwahoBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetWahoData(context,spinner,weatherEntity);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                    }
                });
    }
    //仓库下拉框写入数据
    private void SetWahoData(Context context, MaterialSpinner spinner,wmsabwahoBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<zzasagKeyValueInfo> list = new ArrayList<>();
            list.add(new zzasagKeyValueInfo("0","请选择"));
            for (wmsabwahoBean.outData item : weatherEntity.getOutData()) {
                String key = item.getId()+"";
                String value = item.getWahoname();
                list.add(new zzasagKeyValueInfo(key,value));
            }
            MaterialSpinnerAdapter<zzasagKeyValueInfo> adapter = new MaterialSpinnerAdapter<>(context,list);
            spinner.setAdapter(adapter);
        }else {
            Toast.makeText(context, "没有仓库信息", Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * 用于获取库区下拉框数据，并将数据指定给下拉框
     * @param context 当前页面的上下文
     * @param spinner 需要指定数据的下拉框
     * @param wahoid 用于查询数据的仓库id
     */
    public void GetTrayDataTool(final Context context,final MaterialSpinner spinner,String wahoid){
        zzasadLoad.ShowZZASLoad(context,true);//加载框
        String url = new Appcofig().GetCofigData(context,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("wahoid",wahoid);
        rxjavaService .postMapTray(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsaeTrayBean>() {
                    @Override
                    public void onNext(wmsaeTrayBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetTrayData(context,spinner,weatherEntity);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                    }
                });
    }
    //库区下拉框写入数据
    private void SetTrayData(Context context, MaterialSpinner spinner,wmsaeTrayBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){

            List<zzasagKeyValueInfo> list = new ArrayList<>();
            list.add(new zzasagKeyValueInfo("0","请选择"));
            for (wmsaeTrayBean.outData item : weatherEntity.getOutData()) {
                String key = item.getId()+"";
                String value = item.getTraycode();
                list.add(new zzasagKeyValueInfo(key,value));
            }
            MaterialSpinnerAdapter<zzasagKeyValueInfo> adapter = new MaterialSpinnerAdapter<>(context,list);
            spinner.setAdapter(adapter);
        }else {
            Toast.makeText(context, "没有库区信息", Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * 用于获取库位下拉框数据，并将数据指定给下拉框
     * @param context 当前页面的上下文
     * @param spinner 需要指定数据的下拉框
     * @param trayid 用于查询数据的库区id
     */
    public void GetStolocDataTool(final Context context,final MaterialSpinner spinner,String trayid){
        zzasadLoad.ShowZZASLoad(context,true);//加载框
        String url = new Appcofig().GetCofigData(context,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("trayid",trayid);
        rxjavaService .postMapStoloc(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsafStolocBean>() {
                    @Override
                    public void onNext(wmsafStolocBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetStolocData(context,spinner,weatherEntity);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                    }
                });
    }
    //库位下拉框写入数据
    private void SetStolocData(Context context, MaterialSpinner spinner,wmsafStolocBean weatherEntity){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<zzasagKeyValueInfo> list = new ArrayList<>();
            list.add(new zzasagKeyValueInfo("0","请选择"));
            for (wmsafStolocBean.outData item : weatherEntity.getOutData()) {
                String key = item.getId()+"";
                String value = item.getStoloccode();
                list.add(new zzasagKeyValueInfo(key,value));
            }
            MaterialSpinnerAdapter<zzasagKeyValueInfo> adapter = new MaterialSpinnerAdapter<>(context,list);
            spinner.setAdapter(adapter);
        }else {
            Toast.makeText(context, "没有库位信息", Toast.LENGTH_SHORT).show();
        }
    }



/************************************************入库编辑时根据订单类型用于动态显示信息**************************************************************/

    /***
     * 用于获取仓库下拉框数据，并将数据指定给下拉框
     * @param context 当前页面的上下文
     * @param spinner 需要指定数据的仓库下拉框
     * @param topOrg  用于查询数据的机构id
     * @param outdata  页面传递的值
     * @param trayCodeSpinner  需要初始化的库区下拉
     */
    public void GetWahoDataToolNew(final Context context, final MaterialSpinner spinner, String topOrg,final wmsadEntercomlistBean.outData outdata,final MaterialSpinner trayCodeSpinner  ){
       // zzasadLoad.ShowZZASLoad(context,true);//加载框
        String url = new Appcofig().GetCofigData(context,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);//WeatherService自己定义的接口类
        Map<String,String> myMap = new HashMap<>();
        myMap.put("toporgid",topOrg);
        rxjavaService .postMapWaho(myMap)
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .subscribe(new DisposableObserver<wmsabwahoBean>() {
                    @Override
                    public void onNext(wmsabwahoBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            SetWahoDataNew(context,spinner,weatherEntity,outdata,trayCodeSpinner);//根据返回数据显示下拉信息
                        }else {
                            String str = weatherEntity.getException().getMessage();
                            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) { //线程错误时执行
                        //zzasadLoad.ShowZZASLoad(null,false);
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {//线程完成后执行
                        //zzasadLoad.ShowZZASLoad(null,false);
                        Log.e("线程完成后执行", "线程完成后执行" );
                    }
                });
    }
    //根据订单状况动态仓库下拉框写入数据
    private void SetWahoDataNew(Context context, MaterialSpinner spinner,wmsabwahoBean weatherEntity,wmsadEntercomlistBean.outData outdata,MaterialSpinner trayCodeSpinner){
        int sum = weatherEntity.getOutData().length;
        if(sum > 0){
            List<zzasagKeyValueInfo> list = new ArrayList<>();
            list.add(new zzasagKeyValueInfo("0","请选择"));
            for (wmsabwahoBean.outData item : weatherEntity.getOutData()) {
                String key = item.getId()+"";
                String value = item.getWahoname();
                list.add(new zzasagKeyValueInfo(key,value));
            }
            MaterialSpinnerAdapter<zzasagKeyValueInfo> adapter = new MaterialSpinnerAdapter<>(context,list);
            spinner.setAdapter(adapter);
            //根据传递的数据初始化仓库下拉框显示
            ShowWhoDate(context, spinner, outdata,trayCodeSpinner);
        }else {
            Toast.makeText(context, "没有仓库信息", Toast.LENGTH_SHORT).show();
        }
    }
    //根据传递的数据初始化仓库下拉框显示
    private void ShowWhoDate( Context context,MaterialSpinner spinner,wmsadEntercomlistBean.outData outdata,MaterialSpinner trayCodeSpinner) {
        String key = outdata.getWahoid();
        String value = outdata.getWahoname();
        if (key != null) {
            if (!key.equals("") && !key.equals("null")){
                zzasagKeyValueInfo zzasInfo = new zzasagKeyValueInfo(key, value);
                List<zzasagKeyValueInfo> listspinner = spinner.getItems();
                int position = 0;
                for (int i = 0; i < listspinner.size(); i++) {
                    if (zzasInfo.getKey().equals(listspinner.get(i).getKey())) {
                        position = i;
                        break;
                    }
                }
                spinner.setSelectedIndex(position);
                spinner.setEnabled(false);
                //如果存在仓库数据，初始化库区下拉
                GetTrayDataTool(context, trayCodeSpinner, key);
            }
        }
    }


}
