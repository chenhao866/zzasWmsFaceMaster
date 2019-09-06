/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.xuexiang.xui.XUI;
import com.zzas.aclass.Appcofig;
import com.zzas.awms.wmsapplication.R;

public class Activity17c extends AppCompatActivity implements View.OnClickListener {
    private WebView MyEwbView;
    private ImageButton backBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_17c);
        //按钮
        backBut = findViewById(R.id.backBut);
        backBut.setOnClickListener(this);
        //网页控件
        MyEwbView = findViewById(R.id.MyEwbView);
        MyEwbView.getSettings().setJavaScriptEnabled(true);
        //获取页面传值
        String qruuid =  getIntent().getStringExtra("qruuid");
        String Path = new Appcofig().GetCofigData(this,"ZhiDuPath");//获取配置
        String Url = Path + qruuid;
        MyEwbView.loadUrl(Url);
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(backBut)){
            finish();
        }
    }
    //页面关闭时调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyEwbView.destroy();
    }
}
