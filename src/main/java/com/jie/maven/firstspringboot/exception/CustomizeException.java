package com.jie.maven.firstspringboot.exception;

public class CustomizeException extends  RuntimeException {
    private String msg;
    private int code;
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CustomizeException(String msg) {
        this.msg = msg;
    }
    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode) {
        this.code = iCustomizeErrorCode.getCode();
        this.msg = iCustomizeErrorCode.getMsg();
    }
}
