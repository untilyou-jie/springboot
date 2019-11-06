package com.jie.maven.firstspringboot.dto;

import com.jie.maven.firstspringboot.model.User;
import lombok.Data;

import java.io.StringReader;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private String description;
    private User user;
}
