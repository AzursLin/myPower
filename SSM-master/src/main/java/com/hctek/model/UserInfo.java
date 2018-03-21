package com.hctek.model;

import java.util.Date;

public class UserInfo {
    private String userId;

    private String college;

    private Integer classInfo;

    private Integer dormitory;

    private Date updateDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public Integer getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(Integer classInfo) {
        this.classInfo = classInfo;
    }

    public Integer getDormitory() {
        return dormitory;
    }

    public void setDormitory(Integer dormitory) {
        this.dormitory = dormitory;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}