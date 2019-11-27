package com.jie.maven.firstspringboot.Enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(1,"回复了评论")
    ;
    private int status;
    private String name;

    NotificationTypeEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
