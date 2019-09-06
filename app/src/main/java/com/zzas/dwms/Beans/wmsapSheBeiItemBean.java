/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wmsaqequipmentone 设备检测项
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsapSheBeiItemBean {
    private exception exception;
    private outData[] outData;

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

    public static class  exception {
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
    public static class  outData {
        private String id;
        private String numberid;
        private String number;
        private String devicename;
        private String serialnumber;
        private String task;
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

        public String getNumberid() {
            return numberid;
        }

        public void setNumberid(String numberid) {
            this.numberid = numberid;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDevicename() {
            return devicename;
        }

        public void setDevicename(String devicename) {
            this.devicename = devicename;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
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
