package com.jie.maven.firstspringboot.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;
}
