package com.jie.maven.firstspringboot.controller;


import com.jie.maven.firstspringboot.dto.CommentCreateDto;
import com.jie.maven.firstspringboot.dto.ResultDto;
import com.jie.maven.firstspringboot.exception.CustomizeErrorCode;
import com.jie.maven.firstspringboot.mapper.CommentMapper;
import com.jie.maven.firstspringboot.model.Comment;
import com.jie.maven.firstspringboot.model.User;
import com.jie.maven.firstspringboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request){
       User user  = (User)request.getSession().getAttribute("user");
       if(user==null){
           return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);

       }
        Comment  comment  = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setContent(commentCreateDto.getContent());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);

        comment.setGmtModified(System.currentTimeMillis());
        commentService.insert(comment);

        return  ResultDto.okOf();

    }

}
