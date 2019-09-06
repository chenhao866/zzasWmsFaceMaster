/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wmspstosend 派送管理表
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsanPaiSongBean implements Serializable{
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
        private String clientid;
        private String productcode;
        private String productname;
        private String state;
        private String number;
        private String unit;
        private String consignee;
        private String contact;
        private String shippingaddress;
        private String wahoname;
        private String sendsum;
        private String moblilesum;
        private String wahoid;
        private String outsum;
        private String sendpeople;
        private String sendtype;
        private String area;
        private String substation;
        private String deliverydate;
        private String shippingfee;
        private String remarks;
        private String creaid;
        private String creaorgid;
        private String toporgid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClientid() {
            return clientid;
        }

        public void setClientid(String clientid) {
            this.clientid = clientid;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getShippingaddress() {
            return shippingaddress;
        }

        public void setShippingaddress(String shippingaddress) {
            this.shippingaddress = shippingaddress;
        }

        public String getWahoname() {
            return wahoname;
        }

        public void setWahoname(String wahoname) {
            this.wahoname = wahoname;
        }

        public String getSendsum() {
            return sendsum;
        }

        public void setSendsum(String sendsum) {
            this.sendsum = sendsum;
        }

        public String getMoblilesum() {
            return moblilesum;
        }

        public void setMoblilesum(String moblilesum) {
            this.moblilesum = moblilesum;
        }

        public String getWahoid() {
            return wahoid;
        }

        public void setWahoid(String wahoid) {
            this.wahoid = wahoid;
        }

        public String getOutsum() {
            return outsum;
        }

        public void setOutsum(String outsum) {
            this.outsum = outsum;
        }

        public String getSendpeople() {
            return sendpeople;
        }

        public void setSendpeople(String sendpeople) {
            this.sendpeople = sendpeople;
        }

        public String getSendtype() {
            return sendtype;
        }

        public void setSendtype(String sendtype) {
            this.sendtype = sendtype;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSubstation() {
            return substation;
        }

        public void setSubstation(String substation) {
            this.substation = substation;
        }

        public String getDeliverydate() {
            return deliverydate;
        }

        public void setDeliverydate(String deliverydate) {
            this.deliverydate = deliverydate;
        }

        public String getShippingfee() {
            return shippingfee;
        }

        public void setShippingfee(String shippingfee) {
            this.shippingfee = shippingfee;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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
