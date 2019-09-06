/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

public class zzasaeLogin {
    //登录成功时将muo数据保存为全局
    public void globalFun(zzasaawmsMuo weatherEntity){
        zzasacUserMUO.setOrgTop(weatherEntity.getOrgTop());
        zzasacUserMUO.setRetCode(weatherEntity.getRetCode());
        zzasacUserMUO.setSessionId(weatherEntity.getUserObject().getSessionId());
        zzasacUserMUO.setUniqueId(weatherEntity.getUserObject().getUniqueId());
        zzasacUserMUO.setUserId(weatherEntity.getUserObject().getUserId());
        zzasacUserMUO.setUserMail(weatherEntity.getUserObject().getUserMail());
        zzasacUserMUO.setUserName(weatherEntity.getUserObject().getUserName());
        zzasacUserMUO.setUserOrgId(weatherEntity.getUserObject().getUserOrgId());
        zzasacUserMUO.setUserOrgName(weatherEntity.getUserObject().getUserOrgName());
        zzasacUserMUO.setUserRealName(weatherEntity.getUserObject().getUserRealName());
        zzasacUserMUO.setUserRemoteIP(weatherEntity.getUserObject().getUserRemoteIP());
        zzasacUserMUO.setExtendUserId(weatherEntity.getUserObject().getAttributes().getEXTEND_USER_ID());
        zzasacUserMUO.setTenantId(weatherEntity.getUserObject().getAttributes().getTENANT_ID());
        zzasacUserMUO.setMenutype(weatherEntity.getUserObject().getAttributes().getMenutype());
        zzasacUserMUO.setOrglist(weatherEntity.getUserObject().getAttributes().getOrglist());
        zzasacUserMUO.setParentOrgIds(weatherEntity.getUserObject().getAttributes().getParentOrgIds());
        zzasacUserMUO.setRoleList(weatherEntity.getUserObject().getAttributes().getRoleList());
    }
    //登录返回值状态提示
    public String statusFun(int key){
        switch (key) {
            case 0:
                return "密码错误！";
            case -1:
                return "用户不存在！";
            case -2:
                return "用户无权限登录，请联系系统管理员！";
            case 3:
                return "用户已过期！";
            case 4:
                return "用户未到开始使用时间！";
            case 5:
                return "密码已过期！";
            case -3:
                return "查询用户信息失败，请联系系统管理员检查数据库连接！";
            default:
                return "未知的异常，请联系系统管理员！";
        }
    }



}
