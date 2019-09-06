/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * 客户信息tb_wmsaa_client
 */
package com.zzas.dwms.Beans;

public class wmsaaClientBean {

    private exception exception;
    private outData[] outData;

    public wmsaaClientBean.outData[] getOutData() {
        return outData;
    }

    public void setOutData(wmsaaClientBean.outData[] outData) {
        this.outData = outData;
    }

    public exception getException() {
        return exception;
    }

    public void setException(exception exception) {
        this.exception = exception;
    }


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
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClientcode() {
            return clientcode;
        }

        public void setClientcode(String clientcode) {
            this.clientcode = clientcode;
        }

        public String getCilentname() {
            return cilentname;
        }

        public void setCilentname(String cilentname) {
            this.cilentname = cilentname;
        }

        public String getCilentshort() {
            return cilentshort;
        }

        public void setCilentshort(String cilentshort) {
            this.cilentshort = cilentshort;
        }

        public String getCilentent() {
            return cilentent;
        }

        public void setCilentent(String cilentent) {
            this.cilentent = cilentent;
        }

        public String getCilentlevel() {
            return cilentlevel;
        }

        public void setCilentlevel(String cilentlevel) {
            this.cilentlevel = cilentlevel;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getClientstate() {
            return clientstate;
        }

        public void setClientstate(String clientstate) {
            this.clientstate = clientstate;
        }

        public int getWuliutype() {
            return wuliutype;
        }

        public void setWuliutype(int wuliutype) {
            this.wuliutype = wuliutype;
        }

        public String getCreationtime() {
            return creationtime;
        }

        public void setCreationtime(String creationtime) {
            this.creationtime = creationtime;
        }

        public String getCilentdetails() {
            return cilentdetails;
        }

        public void setCilentdetails(String cilentdetails) {
            this.cilentdetails = cilentdetails;
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

        public int getClientnum() {
            return clientnum;
        }

        public void setClientnum(int clientnum) {
            this.clientnum = clientnum;
        }

        private int id;
        private String clientcode;
        private String cilentname;
        private String cilentshort;
        private String cilentent;
        private String cilentlevel;
        private String postcode;
        private String site;
        private String phone;
        private String fax;
        private String clientstate;
        private int wuliutype;
        private String creationtime;
        private String cilentdetails;
        private int creaid;
        private int creaorgid;
        private int toporgid;
        private int clientnum;


    }
}
