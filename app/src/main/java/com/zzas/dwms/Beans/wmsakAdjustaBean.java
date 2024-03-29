/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsakAdjustaBean {
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
    public static class  exception implements Serializable {
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
        private String dataid;
        private String cilentid;
        private String cilentname;
        private String cilentcode;
        private String wahoid;
        private String wahoname;
        private String productid;
        private String productcode;
        private String productname;
        private String specione;
        private String storid;
        private String storidnew;
        private String storcode;
        private String storcodenew;
        private String trayid;
        private String trayidnew;
        private String traycode;
        private String traycodenew;
        private String sum;
        private String sumnew;
        private String tmpsum;
        private String state;
        private String creationtime;
        private String creaid;
        private String creaname;
        private String creaorgid;
        private String toporgid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDataid() {
            return dataid;
        }

        public void setDataid(String dataid) {
            this.dataid = dataid;
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

        public String getWahoid() {
            return wahoid;
        }

        public void setWahoid(String wahoid) {
            this.wahoid = wahoid;
        }

        public String getWahoname() {
            return wahoname;
        }

        public void setWahoname(String whoaname) {
            this.wahoname = whoaname;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getProductcode() {
            return productcode;
        }

        public void setProductcode(String productcode) {
            this.productcode = productcode;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getSpecione() {
            return specione;
        }

        public void setSpecione(String specione) {
            this.specione = specione;
        }

        public String getStorid() {
            return storid;
        }

        public void setStorid(String storid) {
            this.storid = storid;
        }

        public String getStoridnew() {
            return storidnew;
        }

        public void setStoridnew(String storidnew) {
            this.storidnew = storidnew;
        }

        public String getStorcode() {
            return storcode;
        }

        public void setStorcode(String storcode) {
            this.storcode = storcode;
        }

        public String getStorcodenew() {
            return storcodenew;
        }

        public void setStorcodenew(String storcodenew) {
            this.storcodenew = storcodenew;
        }

        public String getTrayid() {
            return trayid;
        }

        public void setTrayid(String trayid) {
            this.trayid = trayid;
        }

        public String getTrayidnew() {
            return trayidnew;
        }

        public void setTrayidnew(String trayidnew) {
            this.trayidnew = trayidnew;
        }

        public String getTraycode() {
            return traycode;
        }

        public void setTraycode(String traycode) {
            this.traycode = traycode;
        }

        public String getTraycodenew() {
            return traycodenew;
        }

        public void setTraycodenew(String traycodenew) {
            this.traycodenew = traycodenew;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getSumnew() {
            return sumnew;
        }

        public void setSumnew(String sumnew) {
            this.sumnew = sumnew;
        }

        public String getTmpsum() {
            return tmpsum;
        }

        public void setTmpsum(String tmpsum) {
            this.tmpsum = tmpsum;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public String getCreaname() {
            return creaname;
        }

        public void setCreaname(String creaname) {
            this.creaname = creaname;
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
