/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wmsah_outlist 出库清单
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsagOutlistBean implements Serializable {
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


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOutsum() {
            return outsum;
        }

        public void setOutsum(String outsum) {
            this.outsum = outsum;
        }

        public String getCilentid() {
            return cilentid;
        }

        public void setCilentid(String cilentid) {
            this.cilentid = cilentid;
        }

        public String getCilentname() {
            return cilentname;
        }

        public void setCilentname(String cilentname) {
            this.cilentname = cilentname;
        }

        public String getCilentcode() {
            return cilentcode;
        }

        public void setCilentcode(String cilentcode) {
            this.cilentcode = cilentcode;
        }

        public String getCilentsum() {
            return cilentsum;
        }

        public void setCilentsum(String cilentsum) {
            this.cilentsum = cilentsum;
        }

        public String getOuttime() {
            return outtime;
        }

        public void setOuttime(String outtime) {
            this.outtime = outtime;
        }

        public String getWahoid() {
            return wahoid;
        }

        public void setWahoid(String wahoid) {
            this.wahoid = wahoid;
        }

        public String getWahoname() {
            return wahoname;
        }

        public void setWahoname(String wahoname) {
            this.wahoname = wahoname;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getIsyk() {
            return isyk;
        }

        public void setIsyk(String isyk) {
            this.isyk = isyk;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
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

        private String id;
        private String outsum;
        private String cilentid;
        private String cilentname;
        private String cilentcode;
        private String cilentsum;
        private String outtime;
        private String wahoid;
        private String wahoname;
        private String state;
        private String isyk;
        private String text;
        private String creationtime;
        private String creaid;
        private String creaorgid;
        private String toporgid;


    }
}
