/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wms_ysgl_schedulinglist 运输管理调度清单
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsaoYunShuBean implements Serializable{
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
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDispatchnumber() {
            return dispatchnumber;
        }

        public void setDispatchnumber(String dispatchnumber) {
            this.dispatchnumber = dispatchnumber;
        }

        public String getDispatchtype() {
            return dispatchtype;
        }

        public void setDispatchtype(String dispatchtype) {
            this.dispatchtype = dispatchtype;
        }

        public String getDispatchdata() {
            return dispatchdata;
        }

        public void setDispatchdata(String dispatchdata) {
            this.dispatchdata = dispatchdata;
        }

        public String getStationid() {
            return stationid;
        }

        public void setStationid(String stationid) {
            this.stationid = stationid;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getCarid() {
            return carid;
        }

        public void setCarid(String carid) {
            this.carid = carid;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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
        private String dispatchnumber;
        private String dispatchtype;
        private String dispatchdata;
        private String stationid;
        private String station;
        private String carid;
        private String car;
        private String state;
        private String creaid;
        private String creaorgid;
        private String toporgid;



    }
}
