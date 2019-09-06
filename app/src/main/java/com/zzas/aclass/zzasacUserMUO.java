/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

import android.app.Application;

public class zzasacUserMUO extends Application{




    public static int getRetCode() {
        return retCode;
    }

    public static void setRetCode(int retCode) {
        zzasacUserMUO.retCode = retCode;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        zzasacUserMUO.sessionId = sessionId;
    }

    public static String getUniqueId() {
        return uniqueId;
    }

    public static void setUniqueId(String uniqueId) {
        zzasacUserMUO.uniqueId = uniqueId;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        zzasacUserMUO.userId = userId;
    }

    public static String getUserMail() {
        return userMail;
    }

    public static void setUserMail(String userMail) {
        zzasacUserMUO.userMail = userMail;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        zzasacUserMUO.userName = userName;
    }

    public static String getUserOrgId() {
        return userOrgId;
    }

    public static void setUserOrgId(String userOrgId) {
        zzasacUserMUO.userOrgId = userOrgId;
    }

    public static String getUserOrgName() {
        return userOrgName;
    }

    public static void setUserOrgName(String userOrgName) {
        zzasacUserMUO.userOrgName = userOrgName;
    }

    public static String getUserRealName() {
        return userRealName;
    }

    public static void setUserRealName(String userRealName) {
        zzasacUserMUO.userRealName = userRealName;
    }

    public static String getUserRemoteIP() {
        return userRemoteIP;
    }

    public static void setUserRemoteIP(String userRemoteIP) {
        zzasacUserMUO.userRemoteIP = userRemoteIP;
    }

    public static String getExtendUserId() {
        return EXTEND_USER_ID;
    }

    public static void setExtendUserId(String extendUserId) {
        EXTEND_USER_ID = extendUserId;
    }

    public static String getTenantId() {
        return TENANT_ID;
    }

    public static void setTenantId(String tenantId) {
        TENANT_ID = tenantId;
    }

    public static String getMenutype() {
        return menutype;
    }

    public static void setMenutype(String menutype) {
        zzasacUserMUO.menutype = menutype;
    }

    public static String getOrglist() {
        return orglist;
    }

    public static void setOrglist(String orglist) {
        zzasacUserMUO.orglist = orglist;
    }

    public static String getParentOrgIds() {
        return parentOrgIds;
    }

    public static void setParentOrgIds(String parentOrgIds) {
        zzasacUserMUO.parentOrgIds = parentOrgIds;
    }

    public static String getRoleList() {
        return roleList;
    }

    public static void setRoleList(String roleList) {
        zzasacUserMUO.roleList = roleList;
    }

    public static String getOrgTop() {
        return orgTop;
    }

    public static void setOrgTop(String orgTop) {
        zzasacUserMUO.orgTop = orgTop;
    }

    private static String orgTop;
    private static int retCode;
    private static String sessionId;
    private static String uniqueId;
    private static String userId;
    private static String userMail;
    private static String userName;
    private static String userOrgId;
    private static String userOrgName;
    private static String userRealName;
    private static String userRemoteIP;
    private static String EXTEND_USER_ID;
    private static String TENANT_ID;
    private static String menutype;
    private static String orglist;
    private static String parentOrgIds;
    private static String roleList;

}
