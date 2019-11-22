package com.jie.maven.firstspringboot.service;


import com.jie.maven.firstspringboot.Enums.CommenTypeEnum;
import com.jie.maven.firstspringboot.dto.CommentDTO;
import com.jie.maven.firstspringboot.exception.CustomizeErrorCode;
import com.jie.maven.firstspringboot.exception.CustomizeException;
import com.jie.maven.firstspringboot.mapper.*;
import com.jie.maven.firstspringboot.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Transient
    public void insert(Comment comment) {
        if(comment.getParentId()==0|| comment.getCommentator()==null){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);

        }
        if(comment.getType()==null|| !CommenTypeEnum.isExist(comment)){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);

        }
        if(comment.getType()==CommenTypeEnum.COMMENT.getType()){
              //回复评论
            Comment comment1 = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(comment1==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Comment c = new Comment();
            c.setId(comment.getParentId());

            c.setCommentCount(1);
            commentExtMapper.incCommentCount(c);

        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOTFOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }

    }

    public List<CommentDTO> listByParentId(Long id,CommenTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().
                andParentIdEqualTo(id).
                andTypeEqualTo(type.getType());
        //让结果排序
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size()==0){
        return  new ArrayList<>();

        }
        //把comments 的创建者使用creator弄成一个集合
        Set<Long> commeators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long>  userIds = new ArrayList<>();
        UserExample userExample = new UserExample();

        userIds.addAll(commeators);
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user->user.getId(),user->user));
        List<CommentDTO>  commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return  commentDTO;
        }).collect(Collectors.toList());


        return commentDTOS;


    }
}
