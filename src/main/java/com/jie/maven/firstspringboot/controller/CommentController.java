package com.jie.maven.firstspringboot.controller;


import com.jie.maven.firstspringboot.dto.CommentDto;
import com.jie.maven.firstspringboot.mapper.CommentMapper;
import com.jie.maven.firstspringboot.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping("/comment")
    public Object comment(@RequestBody CommentDto commentDto){
        Comment  comment  = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setType(commentDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setContent(commentDto.getContent());
        comment.setCommentator(1);
        commentMapper.insert(comment)
        comment.setGmtModified(comment.getGmtCreate());
        return  null;

    }

}
