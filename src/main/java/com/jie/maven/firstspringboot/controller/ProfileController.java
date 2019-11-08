package com.jie.maven.firstspringboot.controller;

import com.jie.maven.firstspringboot.dto.PaginationDTO;
import com.jie.maven.firstspringboot.dto.QuestionDTO;
import com.jie.maven.firstspringboot.model.Question;
import com.jie.maven.firstspringboot.model.User;
import com.jie.maven.firstspringboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name ="action")String action, Model model, HttpServletRequest request,@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size
                          ){
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");

        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","请登录后重试.");

                return "redirect:/";

        }
        PaginationDTO pagination =questionService.list(user.getId(),page,size);

        model.addAttribute("pagination",pagination);
    return "profile";
    }
}
