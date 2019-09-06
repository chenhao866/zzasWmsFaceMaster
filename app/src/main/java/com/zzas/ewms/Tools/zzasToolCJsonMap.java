/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * json字符串转换为map、list
 */
package com.zzas.ewms.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class zzasToolCJsonMap {
    /***
     * 将json 数组转换为Map 对象
     * @param jsonString
     * @return
     */
    public  Map<String, Object> getMap(String jsonString){
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsonString);   @SuppressWarnings("unchecked")
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()){
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 把json 转换为ArrayList 形式
     * @param jsonString
     * @return
     */
    public  List<Map<String, Object>> getList(String jsonString){
        List<Map<String, Object>> list = null;
        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                list.add(getMap(jsonObject.toString()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
