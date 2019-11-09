package com.jie.maven.firstspringboot.mapper;

import com.jie.maven.firstspringboot.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionExtMapper {
    void incView(Question question);
}
