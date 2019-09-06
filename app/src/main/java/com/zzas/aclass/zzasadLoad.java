/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

import android.app.Application;
import android.content.Context;

import com.zzas.awms.wmsapplication.R;
import com.zzas.bwms.kyloading.KyLoadingBuilder;

public class zzasadLoad extends Application {
    private static KyLoadingBuilder loadingZZAS;
    public static void ShowZZASLoad(Context context,boolean off){
        if(off){
            loadingZZAS = new KyLoadingBuilder(context);
            loadingZZAS.setIcon(R.mipmap.loading);
            loadingZZAS.setText("正在加载中...");
            loadingZZAS.setOutsideTouchable(false);
            loadingZZAS.show();
        }else {
                loadingZZAS.dismiss();
                loadingZZAS = null;
        }
    }
}
