/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wms_ysgl_dispatchlistgoods 调度清单客户表
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsaoYunShuaBean implements Serializable {
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
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTrackingnumber() {
            return trackingnumber;
        }

        public void setTrackingnumber(String trackingnumber) {
            this.trackingnumber = trackingnumber;
        }

        public String getDispatchnumber() {
            return dispatchnumber;
        }

        public void setDispatchnumber(String dispatchnumber) {
            this.dispatchnumber = dispatchnumber;
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

        public String getCilentsum() {
            return cilentsum;
        }

        public void setCilentsum(String cilentsum) {
            this.cilentsum = cilentsum;
        }

        public String getTransportationtype() {
            return transportationtype;
        }

        public void setTransportationtype(String transportationtype) {
            this.transportationtype = transportationtype;
        }

        public String getDetailedaddress() {
            return detailedaddress;
        }

        public void setDetailedaddress(String detailedaddress) {
            this.detailedaddress = detailedaddress;
        }

        public String getWaybilltype() {
            return waybilltype;
        }

        public void setWaybilltype(String waybilltype) {
            this.waybilltype = waybilltype;
        }

        public String getTransporttime() {
            return transporttime;
        }

        public void setTransporttime(String transporttime) {
            this.transporttime = transporttime;
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
        private String trackingnumber;
        private String dispatchnumber;
        private String cilentid;
        private String cilentname;
        private String cilentsum;
        private String transportationtype;
        private String detailedaddress;
        private String waybilltype;
        private String transporttime;
        private String creaid;
        private String creaorgid;
        private String toporgid;
    }
}
