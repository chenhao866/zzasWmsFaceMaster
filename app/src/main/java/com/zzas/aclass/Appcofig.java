/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

public class Appcofig {

    //根据Key值获取对应的配置信息
    public String GetCofigData(Context context,String Key){
        Properties properties = new Properties();
        String cofigip = "";
        try {
            properties.load(context.getAssets().open("appCofig.properties"));
            cofigip = properties.getProperty(Key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cofigip;
    }


}
