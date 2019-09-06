/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * 仓库数据tb_wmsac_manage
 */
package com.zzas.dwms.Beans;

public class wmsabwahoBean {

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

    public static class  exception{
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

    public static class  outData{
        private int id;
        private String wahoname;
        private String wahocode;
        private String wahoclass;
        private String wahosite;
        private String wahostaffname;
        private String wahostaffid;
        private String text;
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

        public String getWahoname() {
            return wahoname;
        }

        public void setWahoname(String wahoname) {
            this.wahoname = wahoname;
        }

        public String getWahocode() {
            return wahocode;
        }

        public void setWahocode(String wahocode) {
            this.wahocode = wahocode;
        }

        public String getWahoclass() {
            return wahoclass;
        }

        public void setWahoclass(String wahoclass) {
            this.wahoclass = wahoclass;
        }

        public String getWahosite() {
            return wahosite;
        }

        public void setWahosite(String wahosite) {
            this.wahosite = wahosite;
        }

        public String getWahostaffname() {
            return wahostaffname;
        }

        public void setWahostaffname(String wahostaffname) {
            this.wahostaffname = wahostaffname;
        }

        public String getWahostaffid() {
            return wahostaffid;
        }

        public void setWahostaffid(String wahostaffid) {
            this.wahostaffid = wahostaffid;
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
