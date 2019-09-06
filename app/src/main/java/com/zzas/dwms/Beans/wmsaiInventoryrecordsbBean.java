/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsaiInventoryrecordsbBean implements Serializable {
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

        public String getDataid() {
            return dataid;
        }

        public void setDataid(String dataid) {
            this.dataid = dataid;
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

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getTmpsum() {
            return tmpsum;
        }

        public void setTmpsum(String tmpsum) {
            this.tmpsum = tmpsum;
        }

        public String getSumcount() {
            return sumcount;
        }

        public void setSumcount(String sumcount) {
            this.sumcount = sumcount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private String id;
        private String dataid;
        private String productid;
        private String productcode;
        private String productname;
        private String specione;
        private String sum;
        private String tmpsum;
        private String sumcount;
        private String state;
        private String text;



    }
}
