/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wms_ysgl_vehicle 车辆管理
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsaoYunShucBean implements Serializable {
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
        private String id;
        private String licensenumber;
        private String drivername;
        private String vehicleattributes;
        private String contactnumber;
        private String ownedsiteid;
        private String ownedsite;
        private String type;
        private String length;
        private String loada;
        private String creaid;
        private String creaorgid;
        private String toporgid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLicensenumber() {
            return licensenumber;
        }

        public void setLicensenumber(String licensenumber) {
            this.licensenumber = licensenumber;
        }

        public String getDrivername() {
            return drivername;
        }

        public void setDrivername(String drivername) {
            this.drivername = drivername;
        }

        public String getVehicleattributes() {
            return vehicleattributes;
        }

        public void setVehicleattributes(String vehicleattributes) {
            this.vehicleattributes = vehicleattributes;
        }

        public String getContactnumber() {
            return contactnumber;
        }

        public void setContactnumber(String contactnumber) {
            this.contactnumber = contactnumber;
        }

        public String getOwnedsiteid() {
            return ownedsiteid;
        }

        public void setOwnedsiteid(String ownedsiteid) {
            this.ownedsiteid = ownedsiteid;
        }

        public String getOwnedsite() {
            return ownedsite;
        }

        public void setOwnedsite(String ownedsite) {
            this.ownedsite = ownedsite;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getLoada() {
            return loada;
        }

        public void setLoada(String loada) {
            this.loada = loada;
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
    }
}
