package com.jie.maven.firstspringboot.model;

public class User {
    private Integer id;
    private String  name;
    private String  accountId;
    //设置cookie
    private String  token;
    private Long gmtCreate;
    private Long gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmeModified() {
        return gmtModified;
    }

    public void setGmeModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}
