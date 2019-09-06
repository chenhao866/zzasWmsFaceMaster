/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.aclass.zzasacUserMUO;
import com.zzas.aclass.zzasagKeyValueInfo;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsadEntercomlistBean;
import com.zzas.ewms.Tools.zzasToolAstorage;
import com.zzas.ewms.Tools.zzasToolBdialog;

import java.util.Calendar;
import java.util.List;

public class Activity08 extends AppCompatActivity implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {
    private wmsadEntercomlistBean ZZasBean;
    private int ZZasPosition;
    private Boolean iswahoMap = false ;
    private Boolean istrayMap = false ;
    private Boolean isstorMap = false ;



    private SuperButton reCheckBut;
    private SuperButton cancelBut;


    private MaterialSpinner wahoName;
    private MaterialSpinner trayCode;
    private MaterialSpinner storCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_08);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);

        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);


        //下拉框
        wahoName = findViewById(R.id.wahoName);
        trayCode = findViewById(R.id.trayCode);
        storCode=findViewById(R.id.storCode);
        wahoName.setOnItemSelectedListener(this);
        trayCode.setOnItemSelectedListener(this);
        storCode.setOnItemSelectedListener(this);

        //获取页面传递过来的值
        ZZasBean = (wmsadEntercomlistBean) getIntent().getSerializableExtra("BeanData");
        ZZasPosition = getIntent().getIntExtra("position",-1);
        if(ZZasPosition < 0 ){
            String str = "页面传值错误！请退出当前页面重新进入";
            String tag = "zzas";
            DialogFun(str,tag);
        }

        //根据订单显示仓库下拉是否可用
        String topOrg = zzasacUserMUO.getOrgTop();//获取顶层机构
        zzasToolAstorage zzasTool = new zzasToolAstorage();
        zzasTool.GetWahoDataToolNew(Activity08.this,wahoName,topOrg,ZZasBean.getOutData()[ZZasPosition],trayCode);
        String wahokey = ZZasBean.getOutData()[ZZasPosition].getWahoid();
        if (wahokey != null ){
            if (!wahokey.equals("") && !wahokey.equals("null")){
                iswahoMap = true;
            }
        }
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//确定事件
            if(verifyFun()){//验证表单是否填写完整
                //获取并保存输入框数据
                ZZasBean.getOutData()[ZZasPosition].setState(1);
                //页面传值
                Intent intent=new Intent();
                intent.putExtra("isQuery", true);
                intent.putExtra("BeanData", ZZasBean);
                setResult(1, intent);
                finish();
            }
        }else if(v.equals(cancelBut)){//取消事件
            finish();
        }
    }
    //禁止输入框输入空格和换行
    public static void setEditTextInputSpace(MaterialEditText editText) {
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

    //下拉框选择事件
    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        String Keyid =  ((zzasagKeyValueInfo)view.getSelectedItem()).getKey();
        String Value = ((zzasagKeyValueInfo)view.getSelectedItem()).getValue();
        if(view.equals(wahoName)){
            trayCode.setItems();
            storCode.setItems();
            iswahoMap = true;
            istrayMap = false;
            isstorMap = false;
            ZZasBean.getOutData()[ZZasPosition].setWahoid(Keyid);
            ZZasBean.getOutData()[ZZasPosition].setWahoname(Value);
            zzasToolAstorage zzasTool = new zzasToolAstorage();
            zzasTool.GetTrayDataTool(Activity08.this,trayCode,Keyid);
        }else if(view.equals(trayCode)){
            istrayMap = true;
            isstorMap = false;
            ZZasBean.getOutData()[ZZasPosition].setTrayid(Keyid);
            ZZasBean.getOutData()[ZZasPosition].setTraycode(Value);
            zzasToolAstorage zzasTool = new zzasToolAstorage();
            zzasTool.GetStolocDataTool(Activity08.this,storCode,Keyid);
        }else if(view.equals(storCode)){
            isstorMap = true;
            ZZasBean.getOutData()[ZZasPosition].setStorid(Keyid);
            ZZasBean.getOutData()[ZZasPosition].setStorcode(Value);
        }
    }

    //验证表单
    private Boolean verifyFun(){
        String tag = "zzasa";
      if(!iswahoMap){
            String str = "入库仓库不能为空";
            DialogFun(str,tag);
            return false;
        }else if(!istrayMap){
            String str = "入库库区不能为空";
            DialogFun(str,tag);
            return false;
        }else if(!isstorMap){
            String str = "入库库位不能为空";
            DialogFun(str,tag);
            return false;
        }
        return true;
    }
    //简单弹窗封装
    private void DialogFun(String str, String tag){
        zzasToolBdialog zzasdialog = new zzasToolBdialog();
        zzasdialog.showSimpleTipDialog(Activity08.this,str,tag,YesClick);
    }
    //弹窗确定按钮点击事件
    MaterialDialog.SingleButtonCallback YesClick = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if(dialog.getTag().equals("zzas")){
                finish();
            }
        }
    };
}
