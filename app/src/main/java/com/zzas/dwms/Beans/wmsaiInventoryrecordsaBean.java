/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wms_inventoryrecordsa 盘点库位表
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsaiInventoryrecordsaBean implements Serializable {
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
        private String id;
        private String dataid;
        private String storid;
        private String storcode;
        private String state;

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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
