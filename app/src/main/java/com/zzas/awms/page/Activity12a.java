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
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.Appcofig;
import com.zzas.aclass.zzasabService;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasadLoad;
import com.zzas.aclass.zzasagKeyValueInfo;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.dwms.Beans.wmsakAdjustaBean;
import com.zzas.ewms.Tools.zzasToolAstorage;
import com.zzas.ewms.Tools.zzasToolBdialog;

public class Activity12a extends AppCompatActivity implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {
    private SuperButton reCheckBut;
    private SuperButton cancelBut;
    private MaterialEditText productCode;
    private MaterialEditText productName;
    private MaterialEditText speciOne;
    private MaterialEditText sum;
    private MaterialEditText sumcount;
    private MaterialSpinner trayId;
    private MaterialSpinner storId;

    private wmsajAdjustBean.outData ZZasmc_bean;
    private wmsakAdjustaBean.outData SetDataBean = new wmsakAdjustaBean.outData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_12a);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        //输入框
        productCode = findViewById(R.id.productCode);
        productName = findViewById(R.id.productName);
        speciOne = findViewById(R.id.speciOne);
        sum = findViewById(R.id.sum);
        sumcount = findViewById(R.id.sumcount);
        //下拉框
        trayId = findViewById(R.id.trayId);
        storId = findViewById(R.id.storId);
        trayId.setOnItemSelectedListener(this);
        storId.setOnItemSelectedListener(this);
        //页面传值初始化
        ZZasmc_bean = (wmsajAdjustBean.outData) getIntent().getSerializableExtra("ZZaswmsacBeanItem");//获取页面传递过来的商品数据
        SetShowPage();
    }
    //初始化显示页面数据
    private void SetShowPage(){
        productCode.setText(ZZasmc_bean.getProductcode());
        productName.setText(ZZasmc_bean.getProductname());
        speciOne.setText(ZZasmc_bean.getSpecione());
        sum.setText(ZZasmc_bean.getTmpsum());
        //根据仓库id赋值库区下拉框
        zzasToolAstorage zzasTool = new zzasToolAstorage();
        zzasTool.GetTrayDataTool(Activity12a.this,trayId,ZZasmc_bean.getWahoid());//
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//确认调库
            if (verifyFun()){
                String json = JsonDataFun();
                SetAdjustaData(json);
            }
        }else if (v.equals(cancelBut)){//取消
            Intent intent=new Intent();
            setResult(1, intent);
            finish();
        }
    }
    //封装json数据
    private String JsonDataFun() {
        SetDataBean.setSumnew(sumcount.getEditValue());
        SetDataBean.setDataid(ZZasmc_bean.getId());
        SetDataBean.setCilentid(ZZasmc_bean.getCilentid());
        SetDataBean.setCilentname(ZZasmc_bean.getCilentname());
        SetDataBean.setCilentcode(ZZasmc_bean.getCilentcode());
        SetDataBean.setWahoid(ZZasmc_bean.getWahoid());
        SetDataBean.setWahoname(ZZasmc_bean.getWahoname());
        SetDataBean.setProductid(ZZasmc_bean.getProductid());
        SetDataBean.setProductcode(ZZasmc_bean.getProductcode());
        SetDataBean.setProductname(ZZasmc_bean.getProductname());
        SetDataBean.setSpecione(ZZasmc_bean.getSpecione());
        SetDataBean.setStorid(ZZasmc_bean.getStorid());
        SetDataBean.setStorcode(ZZasmc_bean.getStorcode());
        SetDataBean.setTrayid(ZZasmc_bean.getTrayid());
        SetDataBean.setTraycode(ZZasmc_bean.getTraycode());
        SetDataBean.setSum(ZZasmc_bean.getSum());
        SetDataBean.setTmpsum(ZZasmc_bean.getTmpsum());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String TimeStr = formatter.format(curDate);
        SetDataBean.setCreationtime(TimeStr);
        SetDataBean.setCreaid(zzasacUserMUO.getUserId());
        SetDataBean.setCreaname(zzasacUserMUO.getUserRealName());
        SetDataBean.setCreaorgid(zzasacUserMUO.getUserOrgId());
        SetDataBean.setToporgid(zzasacUserMUO.getOrgTop());
        Gson gson = new Gson();
        Map<String,wmsakAdjustaBean.outData> map = new HashMap<>();
        map.put("setdata",SetDataBean);
        String str = gson.toJson(map);
        return str;
    }
    //执行调库任务请求接口
    protected void SetAdjustaData(String setdata){
        zzasadLoad.ShowZZASLoad(this,true);//加载框
        String url = new Appcofig().GetCofigData(this,"configIP");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        zzasabService rxjavaService = retrofit.create(zzasabService .class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), setdata);//將json字符串转换为参数
        rxjavaService .postMapAdjusta(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<wmsafStolocBean>() {
                    @Override
                    public void onNext(wmsafStolocBean weatherEntity){//获取数据后执行;WeatherEntity为自己定义的数据类
                        if (weatherEntity.getException() == null){
                            Toast.makeText(getApplicationContext(),"调库任务生成成功！",Toast.LENGTH_LONG).show();
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
    //listView点击事件
    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        String Keyid =  ((zzasagKeyValueInfo)view.getSelectedItem()).getKey();
        String Value = ((zzasagKeyValueInfo)view.getSelectedItem()).getValue();
        if(view.equals(trayId)){
            storId.setItems();
            if (Keyid.equals("0")){
                SetDataBean.setTrayidnew(null);
                SetDataBean.setTraycodenew(null);
                SetDataBean.setStoridnew(null);
                SetDataBean.setStorcodenew(null);
            }else {
                SetDataBean.setTrayidnew(Keyid);
                SetDataBean.setTraycodenew(Value);
            }
            zzasToolAstorage zzasTool = new zzasToolAstorage();
            zzasTool.GetStolocDataTool(Activity12a.this,storId,Keyid);
        }else if (view.equals(storId)){
            if (Keyid.equals("0")){
                SetDataBean.setStoridnew(null);
                SetDataBean.setStorcodenew(null);
            }else {
                SetDataBean.setStoridnew(Keyid);
                SetDataBean.setStorcodenew(Value);
            }

        }
    }
    //验证数据是否填写完整
    private Boolean verifyFun(){
        String tag = "zzasa";
        if (!sumcount.validate()){
            return false;
        }else if (SetDataBean.getTrayidnew() == null){
            String str = "调库库区不能为空";
            DialogFun(str,tag);
            return false;
        }else if (SetDataBean.getStorcodenew() == null){
            String str = "调库库位不能为空";
            DialogFun(str,tag);
            return false;
        }else {
          int sum = Integer.parseInt(ZZasmc_bean.getTmpsum());
          int sumnew = Integer.parseInt(sumcount.getEditValue());
          if(sumnew > sum ){
              String str = "调整数量必须小于等于库存数量";
              DialogFun(str,tag);
              return false;
          }
        }
        return true;
    }
    //简单弹窗封装
    private void DialogFun(String str, String tag){
        zzasToolBdialog zzasdialog = new zzasToolBdialog();
        zzasdialog.showSimpleTipDialog(Activity12a.this,str,tag,YesClick);
    }
    //弹窗确定按钮点击事件
    MaterialDialog.SingleButtonCallback YesClick = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

        }
    };
}
