/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

public class zzasagKeyValueInfo {
    public String key;
    public String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public zzasagKeyValueInfo(String key,String value){
        this.key = key;
        this.value = value;
    }
    @Override
    public String toString(){
        return value;
    }
}
