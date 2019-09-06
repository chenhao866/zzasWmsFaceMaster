/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wmsad_tray库区管理
 */
package com.zzas.dwms.Beans;



public class wmsaeTrayBean {
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

        public String getTraycode() {
            return traycode;
        }

        public void setTraycode(String traycode) {
            this.traycode = traycode;
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



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        private String id;
        private int wahoid;
        private String wahoname;
        private String traycode;
        private String text;
        private String creationtime;
        private int creaid;
        private int creaorgid;
        private int toporgid;


    }
}
