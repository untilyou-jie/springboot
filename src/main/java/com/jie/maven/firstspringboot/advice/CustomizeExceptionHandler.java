package com.jie.maven.firstspringboot.advice;


import com.jie.maven.firstspringboot.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        if(e instanceof CustomizeException){
            model.addAttribute("message",((CustomizeException) e).getMsg());

        }else{
            model.addAttribute("message","服务器太热了 请稍等后再试");
        }

        return new ModelAndView("error");
    }


}

