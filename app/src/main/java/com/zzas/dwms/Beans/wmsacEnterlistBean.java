/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * 入库清单 tb_wmsaf_enterlist
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsacEnterlistBean implements Serializable {
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
        private int id;
        private String entersum;
        private int cilentid;
        private String cilentname;
        private String cilentcode;
        private String cilentsum;
        private int wahoid;
        private String wahoname;
        private int state;
        private int isyk;
        private String text;
        private String detail;
        private String creationtime;
        private int creaid;
        private int creaorgid;
        private int toporgid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEntersum() {
            return entersum;
        }

        public void setEntersum(String entersum) {
            this.entersum = entersum;
        }

        public int getCilentid() {
            return cilentid;
        }

        public void setCilentid(int cilentid) {
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

        public int getWahoid() {
            return wahoid;
        }

        public void setWahoid(int wahoid) {
            this.wahoid = wahoid;
        }

        public String getWahoname() {
            return wahoname;
        }

        public void setWahoname(String wahoname) {
            this.wahoname = wahoname;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getIsyk() {
            return isyk;
        }

        public void setIsyk(int isyk) {
            this.isyk = isyk;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getCreationtime() {
            return creationtime;
        }

        public void setCreationtime(String creationtime) {
            this.creationtime = creationtime;
        }

        public int getCreaid() {
            return creaid;
        }

        public void setCreaid(int creaid) {
            this.creaid = creaid;
        }

        public int getCreaorgid() {
            return creaorgid;
        }

        public void setCreaorgid(int creaorgid) {
            this.creaorgid = creaorgid;
        }

        public int getToporgid() {
            return toporgid;
        }

        public void setToporgid(int toporgid) {
            this.toporgid = toporgid;
        }
    }
}
