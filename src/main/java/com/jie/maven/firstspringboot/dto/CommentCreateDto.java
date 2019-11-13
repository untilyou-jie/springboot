package com.jie.maven.firstspringboot.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private int type;
    private long parentId;
    private String content;
}
