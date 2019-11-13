package com.jie.maven.firstspringboot.advice;


import com.alibaba.fastjson.JSON;
import com.jie.maven.firstspringboot.dto.ResultDto;
import com.jie.maven.firstspringboot.exception.CustomizeErrorCode;
import com.jie.maven.firstspringboot.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

//@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        ResultDto resultDto;
        if("application/json".equals(contentType)){
            if(e instanceof CustomizeException){
                resultDto =  ResultDto.errorOf((CustomizeException)e);

            }else{
                resultDto =   ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                Writer writer= response.getWriter();
                writer .write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return  null;


        }else{
            if(e instanceof CustomizeException){
                model.addAttribute("message",((CustomizeException) e).getMsg());

            }else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMsg());
            }

            return new ModelAndView("error");
        }
    }


}

