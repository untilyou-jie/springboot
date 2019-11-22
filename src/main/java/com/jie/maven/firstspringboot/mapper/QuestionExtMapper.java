package com.jie.maven.firstspringboot.mapper;

import com.jie.maven.firstspringboot.model.Comment;
import com.jie.maven.firstspringboot.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExtMapper {
    void incView(Question question);
    void incCommentCount(Question question);
    List<Question> selectRelated(Question question);
}
