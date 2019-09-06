/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * 入库清单详情表tb_wmsag_entercomlist
 *
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsadEntercomlistBean implements Serializable {

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
        private String cilentid;
        private String cilentname;
        private String cilentcode;
        private String productid;
        private String productcode;
        private String productname;
        private String specione;
        private String wahoid;
        private String wahoname;
        private String storid;
        private String storcode;
        private String trayid;
        private String traycode;
        private String series;
        private int predictsum;
        private String sum;
        private int state;
        private String difference;
        private String detailtext;
        private String predicttime;
        private String entertime;
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

        public String getStorid() {
            return storid;
        }

        public void setStorid(String storid) {
            this.storid = storid;
        }

        public String getStorcode() {
            return storcode;
        }

        public void setStorcode(String storcode) {
            this.storcode = storcode;
        }

        public String getTrayid() {
            return trayid;
        }

        public void setTrayid(String trayid) {
            this.trayid = trayid;
        }

        public String getTraycode() {
            return traycode;
        }

        public void setTraycode(String traycode) {
            this.traycode = traycode;
        }

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public int getPredictsum() {
            return predictsum;
        }

        public void setPredictsum(int predictsum) {
            this.predictsum = predictsum;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getDifference() {
            return difference;
        }

        public void setDifference(String difference) {
            this.difference = difference;
        }

        public String getDetailtext() {
            return detailtext;
        }

        public void setDetailtext(String detailtext) {
            this.detailtext = detailtext;
        }

        public String getPredicttime() {
            return predicttime;
        }

        public void setPredicttime(String predicttime) {
            this.predicttime = predicttime;
        }

        public String getEntertime() {
            return entertime;
        }

        public void setEntertime(String entertime) {
            this.entertime = entertime;
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
