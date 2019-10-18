package com.sun.common.entity;

import java.io.Serializable;

public class Waiter implements Serializable {
    private String code;

    private String userName;

    private String tel;

    private String age;

    private String sex;

    private String status;

    private String des;

    private String idCard;

    private String idCardType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public Waiter() {
        super();
    }
    public Waiter(String code, String userName, String tel, String age, String sex, String status, String des, String idCard, String idCardType) {
        this.code = code;
        this.userName = userName;
        this.tel = tel;
        this.age = age;
        this.sex = sex;
        this.status = status;
        this.des = des;
        this.idCard = idCard;
        this.idCardType = idCardType;
    }
}
