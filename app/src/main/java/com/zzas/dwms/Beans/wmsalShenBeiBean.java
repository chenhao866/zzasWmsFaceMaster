/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wmsaqeqcheckrecord 设备巡检项表
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsalShenBeiBean implements Serializable {
    public exception getException() {
        return exception;
    }

    public void setException(exception exception) {
        this.exception = exception;
    }

    public outData[] getOutData() {
        return outData;
    }

    public void setOutData(outData[] outData) {
        this.outData = outData;
    }

    private exception exception;
    private outData[] outData;

    public static class  exception implements Serializable{
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Boolean getInvalid() {
            return invalid;
        }

        public void setInvalid(Boolean invalid) {
            this.invalid = invalid;
        }

        public String getLoginPage() {
            return loginPage;
        }

        public void setLoginPage(String loginPage) {
            this.loginPage = loginPage;
        }

        private String code;
        private String message;
        private Boolean invalid;
        private String loginPage;

    }
    public static class  outData implements Serializable{
        private String id;
        private String uuid;
        private String checktype;
        private String state;
        private String orgid;
        private String orgname;
        private String checkingpeopleid;
        private String checkingpeople;
        private String inspectiondate;
        private String endtime;
        private String creationtime;
        private String creaid;
        private String creaorgid;
        private String toporgid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getChecktype() {
            return checktype;
        }

        public void setChecktype(String checktype) {
            this.checktype = checktype;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getOrgname() {
            return orgname;
        }

        public void setOrgname(String orgname) {
            this.orgname = orgname;
        }

        public String getCheckingpeopleid() {
            return checkingpeopleid;
        }

        public void setCheckingpeopleid(String checkingpeopleid) {
            this.checkingpeopleid = checkingpeopleid;
        }

        public String getCheckingpeople() {
            return checkingpeople;
        }

        public void setCheckingpeople(String checkingpeople) {
            this.checkingpeople = checkingpeople;
        }

        public String getInspectiondate() {
            return inspectiondate;
        }

        public void setInspectiondate(String inspectiondate) {
            this.inspectiondate = inspectiondate;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getCreationtime() {
            return creationtime;
        }

        public void setCreationtime(String creationtime) {
            this.creationtime = creationtime;
        }

        public String getCreaid() {
            return creaid;
        }

        public void setCreaid(String creaid) {
            this.creaid = creaid;
        }

        public String getCreaorgid() {
            return creaorgid;
        }

        public void setCreaorgid(String creaorgid) {
            this.creaorgid = creaorgid;
        }

        public String getToporgid() {
            return toporgid;
        }

        public void setToporgid(String toporgid) {
            this.toporgid = toporgid;
        }
    }
}
