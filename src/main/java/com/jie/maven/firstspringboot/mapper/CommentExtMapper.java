package com.jie.maven.firstspringboot.mapper;

import com.jie.maven.firstspringboot.model.Comment;
import com.jie.maven.firstspringboot.model.CommentExample;
import com.jie.maven.firstspringboot.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentExtMapper {
    void incCommentCount(Comment comment);
}