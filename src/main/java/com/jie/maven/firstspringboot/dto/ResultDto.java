package com.jie.maven.firstspringboot.dto;

import com.jie.maven.firstspringboot.exception.CustomizeErrorCode;
import com.jie.maven.firstspringboot.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto<T> {
    private int code;
    private String message;
    private T data;
    public static ResultDto errorOf(int code,String message){
        ResultDto rd = new ResultDto();
        rd.setCode(code);
        rd.setMessage(message);
        return  rd;
    }


    public static ResultDto errorOf(CustomizeErrorCode noLogin) {
        return errorOf(noLogin.getCode(),noLogin.getMsg());
    }
    public static ResultDto okOf(){
        ResultDto rd = new ResultDto();
        rd.setCode(200);
        rd.setMessage("登陆成功");
        return  rd;

    }
    ///
    public static <T> ResultDto okOf(T t){
        ResultDto rd = new ResultDto();
        rd.setCode(200);
        rd.setMessage("登陆成功");
        rd.setData(t);
        return  rd;

    }

    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
}
