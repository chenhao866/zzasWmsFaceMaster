/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * tb_wms_ysgl_listofwaybillitems 运单管理商品列表
 */
package com.zzas.dwms.Beans;

import java.io.Serializable;

public class wmsaoYunShubBean implements Serializable {
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

        public String getTrackingnumber() {
            return trackingnumber;
        }

        public void setTrackingnumber(String trackingnumber) {
            this.trackingnumber = trackingnumber;
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

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getProductcode() {
            return productcode;
        }

        public void setProductcode(String productcode) {
            this.productcode = productcode;
        }

        public String getWaybilltype() {
            return waybilltype;
        }

        public void setWaybilltype(String waybilltype) {
            this.waybilltype = waybilltype;
        }

        public String getTransportationtype() {
            return transportationtype;
        }

        public void setTransportationtype(String transportationtype) {
            this.transportationtype = transportationtype;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getDetailedaddress() {
            return detailedaddress;
        }

        public void setDetailedaddress(String detailedaddress) {
            this.detailedaddress = detailedaddress;
        }

        public String getTransporttime() {
            return transporttime;
        }

        public void setTransporttime(String transporttime) {
            this.transporttime = transporttime;
        }

        public String getPackaging() {
            return packaging;
        }

        public void setPackaging(String packaging) {
            this.packaging = packaging;
        }

        public String getOweight() {
            return oweight;
        }

        public void setOweight(String oweight) {
            this.oweight = oweight;
        }

        public String getBulk() {
            return bulk;
        }

        public void setBulk(String bulk) {
            this.bulk = bulk;
        }

        public String getUnitprice() {
            return unitprice;
        }

        public void setUnitprice(String unitprice) {
            this.unitprice = unitprice;
        }

        public String getBasicfreight() {
            return basicfreight;
        }

        public void setBasicfreight(String basicfreight) {
            this.basicfreight = basicfreight;
        }

        public String getDeliveryexpense() {
            return deliveryexpense;
        }

        public void setDeliveryexpense(String deliveryexpense) {
            this.deliveryexpense = deliveryexpense;
        }

        public String getChargemode() {
            return chargemode;
        }

        public void setChargemode(String chargemode) {
            this.chargemode = chargemode;
        }

        public String getLandingcharges() {
            return landingcharges;
        }

        public void setLandingcharges(String landingcharges) {
            this.landingcharges = landingcharges;
        }

        public String getTotalcost() {
            return totalcost;
        }

        public void setTotalcost(String totalcost) {
            this.totalcost = totalcost;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCapitalprojects() {
            return capitalprojects;
        }

        public void setCapitalprojects(String capitalprojects) {
            this.capitalprojects = capitalprojects;
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
        private String cilentid;
        private String cilentname;
        private String productid;
        private String productname;
        private String productcode;
        private String waybilltype;
        private String transportationtype;
        private String sum;
        private String detailedaddress;
        private String transporttime;
        private String packaging;
        private String oweight;
        private String bulk;
        private String unitprice;
        private String basicfreight;
        private String deliveryexpense;
        private String chargemode;
        private String landingcharges;
        private String totalcost;
        private String remark;
        private String capitalprojects;
        private String creaid;
        private String creaorgid;
        private String toporgid;



    }
}
