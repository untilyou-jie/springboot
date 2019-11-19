package com.jie.maven.firstspringboot.controller;


import com.jie.maven.firstspringboot.Enums.CommenTypeEnum;
import com.jie.maven.firstspringboot.dto.CommentDTO;
import com.jie.maven.firstspringboot.dto.QuestionDTO;
import com.jie.maven.firstspringboot.model.Comment;
import com.jie.maven.firstspringboot.service.CommentService;
import com.jie.maven.firstspringboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model){
        QuestionDTO question = questionService.getById(id);
        List<CommentDTO> commentDTOS = commentService.listByParentId(id, CommenTypeEnum.QUESTION);
        model.addAttribute("question",question);
        model.addAttribute("comments",commentDTOS);
        //增加问题次数
        questionService.viewCount(id);
        return "question";
    }

}
