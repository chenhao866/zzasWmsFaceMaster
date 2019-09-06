/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.awms.wmsapplication.R;
import com.zzas.dwms.Beans.wmsahOutcomlistBean;
import com.zzas.ewms.Tools.zzasToolBdialog;

public class Activity10b extends AppCompatActivity implements View.OnClickListener {
    private wmsahOutcomlistBean ZZasBean;
    private int ZZasPosition;
    private SuperButton reCheckBut;
    private SuperButton cancelBut;
    private TextView productCode;
    private TextView productName;
    private TextView speciOne;
    private TextView wahoName;
    private TextView trayCode;
    private TextView storCode;
    private TextView sum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10b);
        //TextView
        productCode = findViewById(R.id.productCode);
        productName = findViewById(R.id.productName);
        speciOne = findViewById(R.id.speciOne);
        wahoName = findViewById(R.id.wahoName);
        trayCode = findViewById(R.id.trayCode);
        storCode = findViewById(R.id.storCode);
        sum = findViewById(R.id.sum);
        //按钮
        reCheckBut = findViewById(R.id.reCheckBut);
        cancelBut = findViewById(R.id.cancelBut);
        reCheckBut.setOnClickListener(this);
        cancelBut.setOnClickListener(this);
        //获取页面传递过来的值
        ZZasBean = (wmsahOutcomlistBean) getIntent().getSerializableExtra("BeanData");
        ZZasPosition = getIntent().getIntExtra("position",-1);
        if(ZZasPosition < 0 ){
            String str = "页面传值错误！请退出当前页面重新进入";
            String tag = "zzas";
            DialogFun(str,tag);
        }else {
            SetPageData();
        }
    }
    //根据页面传值初始化页面显示
    private void SetPageData(){
        productCode.setText(ZZasBean.getOutData()[ZZasPosition].getProductcode());
        productName.setText(ZZasBean.getOutData()[ZZasPosition].getProductname());
        speciOne.setText(ZZasBean.getOutData()[ZZasPosition].getSpecione());
        wahoName.setText(ZZasBean.getOutData()[ZZasPosition].getWahoname());
        trayCode.setText(ZZasBean.getOutData()[ZZasPosition].getTraycode());
        storCode.setText(ZZasBean.getOutData()[ZZasPosition].getStorcode());
        sum.setText(ZZasBean.getOutData()[ZZasPosition].getSum());

    }
    //简单弹窗封装
    private void DialogFun(String str, String tag){
        zzasToolBdialog zzasdialog = new zzasToolBdialog();
        zzasdialog.showSimpleTipDialog(Activity10b.this,str,tag,YesClick);
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
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if(v.equals(reCheckBut)){
            ZZasBean.getOutData()[ZZasPosition].setState("1");
            //页面传值
            Intent intent=new Intent();
            intent.putExtra("isQuery", true);
            intent.putExtra("BeanData", ZZasBean);
            setResult(1, intent);
            finish();
        }else if(v.equals(cancelBut)){
            finish();
        }

    }
}
