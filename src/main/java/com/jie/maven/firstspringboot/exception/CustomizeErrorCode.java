package com.jie.maven.firstspringboot.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOTFOUND("你找的消息不存在，要不要换一个再试试？")
    ;

    private String msg;

    CustomizeErrorCode(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;

    }
}
