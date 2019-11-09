package com.jie.maven.firstspringboot.exception;

public class CustomizeException extends  RuntimeException {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public CustomizeException(String msg) {
        this.msg = msg;
    }
    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode) {
        this.msg = iCustomizeErrorCode.getMsg();
    }
}
