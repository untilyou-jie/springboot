package com.jie.maven.firstspringboot.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String  name;
    private String  accountId;
    private String avatarUrl;

    //设置cookie
    private String  token;
    private Long gmtCreate;
    private Long gmtModified;


}
