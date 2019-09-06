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



public class Activity05 extends AppCompatActivity implements View.OnClickListener{
   private wmsadEntercomlistBean ZZasBean;
   private int ZZasPosition;



   private SuperButton reCheckBut;
   private SuperButton cancelBut;
   private ImageButton calendarBut;
   private MaterialEditText series;
   private MaterialEditText sum;
   private MaterialEditText enterTime;
   private MultiLineEditText difference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_05);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        calendarBut = findViewById(R.id.calendarBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        calendarBut.setOnClickListener(this);
        //输入框
        series = findViewById(R.id.series);
        sum = findViewById(R.id.sum);
        enterTime = findViewById(R.id.enterTime);
        difference = findViewById(R.id.difference);
        setEditTextInputSpace(series);
        setEditTextInputSpace(sum);

        //获取页面传递过来的值
        ZZasBean = (wmsadEntercomlistBean) getIntent().getSerializableExtra("BeanData");
        ZZasPosition = getIntent().getIntExtra("position",-1);
        if(ZZasPosition < 0 ){
            String str = "页面传值错误！请退出当前页面重新进入";
            String tag = "zzas";
            DialogFun(str,tag);
        }
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(reCheckBut)){//确定事件
            if(verifyFun()){//验证表单是否填写完整
                //获取并保存输入框数据
                String str = difference.getContentText();//获取差异备注
                ZZasBean.getOutData()[ZZasPosition].setDifference(str);
                String strTime = enterTime.getEditValue();//获取入库时间
                ZZasBean.getOutData()[ZZasPosition].setEntertime(strTime);
                String strseries = series.getEditValue();//获取序列号
                ZZasBean.getOutData()[ZZasPosition].setSeries(strseries);
                String strsum = sum.getEditValue();//获取实际数量
                ZZasBean.getOutData()[ZZasPosition].setSum(strsum);
                ZZasBean.getOutData()[ZZasPosition].setState(2);
                //页面传值
                Intent intent=new Intent();
                intent.putExtra("isQuery", true);
                intent.putExtra("BeanData", ZZasBean);
                setResult(1, intent);
                finish();
            }
        }else if(v.equals(calendarBut)){//显示日期
            showDatePickerDialog(Activity05.this,5,Calendar.getInstance());
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
    //日期选择框
    public void showDatePickerDialog(Context context, int themeResId, Calendar calendar) {
        new DatePickerDialog(context,themeResId,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,int monthOfYear,int dayOfMonth) {
               String str = String.format("%d-%d-%d", year, (monthOfYear + 1), dayOfMonth);
                enterTime.setText(str);
            }
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH))
        .show();
    }

    //验证表单
    private Boolean verifyFun(){
        String tag = "zzasa";
        if(!series.validate() || !sum.validate() || !enterTime.validate()){
            return false;
        }
        return true;
    }
    //简单弹窗封装
    private void DialogFun(String str, String tag){
        zzasToolBdialog zzasdialog = new zzasToolBdialog();
        zzasdialog.showSimpleTipDialog(Activity05.this,str,tag,YesClick);
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
