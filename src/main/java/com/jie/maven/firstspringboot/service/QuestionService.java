package com.jie.maven.firstspringboot.service;

import com.jie.maven.firstspringboot.dto.PaginationDTO;
import com.jie.maven.firstspringboot.dto.QuestionDTO;
import com.jie.maven.firstspringboot.mapper.QuestionMapper;
import com.jie.maven.firstspringboot.mapper.UserMapper;
import com.jie.maven.firstspringboot.model.Question;
import com.jie.maven.firstspringboot.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size*(page-1);
        List<Question> questions =questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS =new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questions) {

            //questin 的Creator 等于user的id
         User user =  userMapper.findById(question.getCreator());
         QuestionDTO questionDTO = new QuestionDTO();
         //srpingboot的工具类  可以映射类 通过系统的属性名
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
            
        }
        paginationDTO.setQuestions(questionDTOS);
        Integer count = questionMapper.count();
        Integer totalPage;
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        return paginationDTO;
    }


    public PaginationDTO list(Integer id, Integer page, Integer size) {
        Integer offset = size*(page-1);
        List<Question> questions =questionMapper.listByUserId(id,offset,size);
        List<QuestionDTO> questionDTOS =new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        User user =null;
        for (Question question : questions) {

            //questin 的Creator 等于user的id
            user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //srpingboot的工具类  可以映射类 通过系统的属性名
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOS);
        Integer count = questionMapper.countByUserId(id);
        Integer totalPage;
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        //srpingboot的工具类  可以映射类 通过系统的属性名
        BeanUtils.copyProperties(question,questionDTO);
        User user =  userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            questionMapper.create(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
