package com.jie.maven.firstspringboot.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOTFOUND(2001,"你找的消息不存在，要不要换一个再试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    ;
    private Integer code;
    private String msg;

    CustomizeErrorCode(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;

    }

    @Override
    public Integer getCode() {
        return code;
    }
}
