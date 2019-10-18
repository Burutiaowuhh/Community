package com.mao.community.model;

public class User {

    private String accountid;
    private String name;
    private String token;
    private Long gmtcreate;
    private Long gmtmodified;


    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmtcreate() {
        return gmtcreate;
    }

    public void setGmtcreate(Long gmtcreate) {
        this.gmtcreate = gmtcreate;
    }

    public Long getGmtmodified() {
        return gmtmodified;
    }

    public void setGmtmodified(Long gmtmodified) {
        this.gmtmodified = gmtmodified;
    }

    @Override
    public String toString() {
        return "User{" +
                "accountid='" + accountid + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", gmtcreate=" + gmtcreate +
                ", gmtmodified=" + gmtmodified +
                '}';
    }
}
