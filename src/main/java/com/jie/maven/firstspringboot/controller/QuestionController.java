package com.jie.maven.firstspringboot.controller;


import com.jie.maven.firstspringboot.dto.QuestionDTO;
import com.jie.maven.firstspringboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("question",question);
        //增加问题次数
        questionService.viewCount(id);
        return "question";
    }

}
