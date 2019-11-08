package com.jie.maven.firstspringboot.controller;

import com.jie.maven.firstspringboot.dto.QuestionDTO;
import com.jie.maven.firstspringboot.mapper.QuestionMapper;
import com.jie.maven.firstspringboot.mapper.UserMapper;
import com.jie.maven.firstspringboot.model.Question;
import com.jie.maven.firstspringboot.model.User;
import com.jie.maven.firstspringboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id ,Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());

        model.addAttribute("tag",question.getTag());
        return "publish";

    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";

    }
    @PostMapping("/publish")
    public String dopublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value="id",required = false) Long id,
                            HttpServletRequest request, Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);

        model.addAttribute("tag",tag);

        if (title==null || title=="") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description==null || description=="") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag==null || tag=="") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","请登录后重试.");
            return "publish";

        }
        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";

    }

}
