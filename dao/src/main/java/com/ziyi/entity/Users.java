package com.ziyi.entity;

import java.util.Date;

/**
 * auther:jurzis
 * date: 2021/4/29 15:41
 */
public class Users {

    private Integer userId;

    private String userName;

    private String userPhone;

    private String password;

    private String salt;

    private Date createTime;

    private Integer createUser;

    private Date modifyTime;

    private Integer modifyUser;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", modifyTime=" + modifyTime +
                ", modifyUser=" + modifyUser +
                '}';
    }
}
