/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wms_inventoryrecords 盘点任务表
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsaiInventoryrecordsBean implements Serializable {
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
        private String inventorytype;
        private String syarttime;
        private String endtime;
        private String whoaid;
        private String whoaname;
        private String inventoryareaid;
        private String inventoryarea;
        private String inventorypersonnelid;
        private String inventorypersonnel;
        private String reviewingofficerid;
        private String reviewingofficer;
        private String snventorynumber;
        private String state;
        private String inspectorsid;
        private String inspectors;
        private String checkthetime;
        private String inventoryresults;
        private String creaid;
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

        public String getInventorytype() {
            return inventorytype;
        }

        public void setInventorytype(String inventorytype) {
            this.inventorytype = inventorytype;
        }

        public String getSyarttime() {
            return syarttime;
        }

        public void setSyarttime(String syarttime) {
            this.syarttime = syarttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getWhoaid() {
            return whoaid;
        }

        public void setWhoaid(String whoaid) {
            this.whoaid = whoaid;
        }

        public String getWhoaname() {
            return whoaname;
        }

        public void setWhoaname(String whoaname) {
            this.whoaname = whoaname;
        }

        public String getInventoryareaid() {
            return inventoryareaid;
        }

        public void setInventoryareaid(String inventoryareaid) {
            this.inventoryareaid = inventoryareaid;
        }

        public String getInventoryarea() {
            return inventoryarea;
        }

        public void setInventoryarea(String inventoryarea) {
            this.inventoryarea = inventoryarea;
        }

        public String getInventorypersonnelid() {
            return inventorypersonnelid;
        }

        public void setInventorypersonnelid(String inventorypersonnelid) {
            this.inventorypersonnelid = inventorypersonnelid;
        }

        public String getInventorypersonnel() {
            return inventorypersonnel;
        }

        public void setInventorypersonnel(String inventorypersonnel) {
            this.inventorypersonnel = inventorypersonnel;
        }

        public String getReviewingofficerid() {
            return reviewingofficerid;
        }

        public void setReviewingofficerid(String reviewingofficerid) {
            this.reviewingofficerid = reviewingofficerid;
        }

        public String getReviewingofficer() {
            return reviewingofficer;
        }

        public void setReviewingofficer(String reviewingofficer) {
            this.reviewingofficer = reviewingofficer;
        }

        public String getSnventorynumber() {
            return snventorynumber;
        }

        public void setSnventorynumber(String snventorynumber) {
            this.snventorynumber = snventorynumber;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getInspectorsid() {
            return inspectorsid;
        }

        public void setInspectorsid(String inspectorsid) {
            this.inspectorsid = inspectorsid;
        }

        public String getInspectors() {
            return inspectors;
        }

        public void setInspectors(String inspectors) {
            this.inspectors = inspectors;
        }

        public String getCheckthetime() {
            return checkthetime;
        }

        public void setCheckthetime(String checkthetime) {
            this.checkthetime = checkthetime;
        }

        public String getInventoryresults() {
            return inventoryresults;
        }

        public void setInventoryresults(String inventoryresults) {
            this.inventoryresults = inventoryresults;
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
