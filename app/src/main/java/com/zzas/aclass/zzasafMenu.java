/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

public class zzasafMenu {
    private exception exception;
    private outData[] outData;

    public zzasafMenu.outData[] getOutData() {
        return outData;
    }

    public void setOutData(zzasafMenu.outData[] outData) {
        this.outData = outData;
    }



    public exception getException() {
        return exception;
    }

    public void setException(exception exception) {
        this.exception = exception;
    }


    public static class  exception{
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

    public static class  outData{
        private int id;
        private int levelindex;
        private String textzh;
        private String texten;
        private int pid;
        private int indexsort;
        private int rtuindex;
        private int chilnode;
        private String pagesrc;
        private String classico;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevelindex() {
            return levelindex;
        }

        public void setLevelindex(int levelindex) {
            this.levelindex = levelindex;
        }

        public String getTextzh() {
            return textzh;
        }

        public void setTextzh(String textzh) {
            this.textzh = textzh;
        }

        public String getTexten() {
            return texten;
        }

        public void setTexten(String texten) {
            this.texten = texten;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getIndexsort() {
            return indexsort;
        }

        public void setIndexsort(int indexsort) {
            this.indexsort = indexsort;
        }

        public int getRtuindex() {
            return rtuindex;
        }

        public void setRtuindex(int rtuindex) {
            this.rtuindex = rtuindex;
        }

        public int getChilnode() {
            return chilnode;
        }

        public void setChilnode(int chilnode) {
            this.chilnode = chilnode;
        }

        public String getPagesrc() {
            return pagesrc;
        }

        public void setPagesrc(String pagesrc) {
            this.pagesrc = pagesrc;
        }

        public String getClassico() {
            return classico;
        }

        public void setClassico(String classico) {
            this.classico = classico;
        }
    }
}
