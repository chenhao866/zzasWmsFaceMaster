/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

public  class  zzasaawmsMuo {
    public String getOrgTop() {
        return orgTop;
    }

    public void setOrgTop(String orgTop) {
        this.orgTop = orgTop;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public UserObject getUserObject() {
        return userObject;
    }

    public void setUserObject(UserObject userObject) {
        this.userObject = userObject;
    }

    public String orgTop;
    public int retCode;
    public UserObject userObject;

    public static class UserObject{
        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserMail() {
            return userMail;
        }

        public void setUserMail(String userMail) {
            this.userMail = userMail;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserOrgId() {
            return userOrgId;
        }

        public void setUserOrgId(String userOrgId) {
            this.userOrgId = userOrgId;
        }

        public String getUserOrgName() {
            return userOrgName;
        }

        public void setUserOrgName(String userOrgName) {
            this.userOrgName = userOrgName;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getUserRemoteIP() {
            return userRemoteIP;
        }

        public void setUserRemoteIP(String userRemoteIP) {
            this.userRemoteIP = userRemoteIP;
        }

        public attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(attributes attributes) {
            this.attributes = attributes;
        }

        public attributes attributes;
        public String sessionId;
        public String uniqueId;
        public String userId;
        public String userMail;
        public String userName;
        public String userOrgId;
        public String userOrgName;
        public String userRealName;
        public String userRemoteIP;

        public static class attributes{
            public String EXTEND_USER_ID;
            public String TENANT_ID;
            public String menutype;
            public String orglist;
            public String parentOrgIds;
            public String roleList;

            public String getEXTEND_USER_ID() {
                return EXTEND_USER_ID;
            }

            public void setEXTEND_USER_ID(String EXTEND_USER_ID) {
                this.EXTEND_USER_ID = EXTEND_USER_ID;
            }

            public String getTENANT_ID() {
                return TENANT_ID;
            }

            public void setTENANT_ID(String TENANT_ID) {
                this.TENANT_ID = TENANT_ID;
            }

            public String getMenutype() {
                return menutype;
            }

            public void setMenutype(String menutype) {
                this.menutype = menutype;
            }

            public String getOrglist() {
                return orglist;
            }

            public void setOrglist(String orglist) {
                this.orglist = orglist;
            }

            public String getParentOrgIds() {
                return parentOrgIds;
            }

            public void setParentOrgIds(String parentOrgIds) {
                this.parentOrgIds = parentOrgIds;
            }

            public String getRoleList() {
                return roleList;
            }

            public void setRoleList(String roleList) {
                this.roleList = roleList;
            }
        }


    }

}
