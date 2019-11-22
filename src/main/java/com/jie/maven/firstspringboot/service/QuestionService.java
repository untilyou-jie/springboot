package com.jie.maven.firstspringboot.service;

import com.jie.maven.firstspringboot.dto.PaginationDTO;
import com.jie.maven.firstspringboot.dto.QuestionDTO;
import com.jie.maven.firstspringboot.exception.CustomizeErrorCode;
import com.jie.maven.firstspringboot.exception.CustomizeException;
import com.jie.maven.firstspringboot.mapper.QuestionExtMapper;
import com.jie.maven.firstspringboot.mapper.QuestionMapper;
import com.jie.maven.firstspringboot.mapper.UserMapper;
import com.jie.maven.firstspringboot.model.Question;
import com.jie.maven.firstspringboot.model.QuestionExample;
import com.jie.maven.firstspringboot.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size*(page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions =questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size
        ));
        List<QuestionDTO> questionDTOS =new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questions) {

            //questin 的Creator 等于user的id
         User user =  userMapper.selectByPrimaryKey(question.getCreator());
         QuestionDTO questionDTO = new QuestionDTO();
         //srpingboot的工具类  可以映射类 通过系统的属性名
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
            
        }
        paginationDTO.setQuestions(questionDTOS);
        Integer count = (int)questionMapper.countByExample(new QuestionExample());
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


    public PaginationDTO list(long id, Integer page, Integer size) {
        Integer offset = size*(page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> questions =questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size
        ));

        List<QuestionDTO> questionDTOS =new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        User user =null;
        for (Question question : questions) {

            //questin 的Creator 等于user的id
            user =  userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //srpingboot的工具类  可以映射类 通过系统的属性名
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOS);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        Integer count = (int)questionMapper.countByExample(example);
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

    public QuestionDTO getById(long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOTFOUND);

        }

        QuestionDTO questionDTO = new QuestionDTO();
        //srpingboot的工具类  可以映射类 通过系统的属性名
        BeanUtils.copyProperties(question,questionDTO);
        User user =  userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);

        }else{
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int result = questionMapper.updateByExampleSelective(updateQuestion,example);
            if(result!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOTFOUND);

            }
        }
    }

    public void viewCount(long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        updateQuestion.setId(question.getId());
        updateQuestion.setViewCount(1);
        questionExtMapper.incView(updateQuestion);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDto) {
        if(StringUtils.isBlank(queryDto.getTag())){
                return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDto.getTag(),",");
        //给每一个剪切后的字符串加上|
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q->{
            QuestionDTO questionDTO   = new QuestionDTO();
           BeanUtils.copyProperties(q,questionDTO);
           return questionDTO;

                }).collect(Collectors.toList());


        return questionDTOS;
    }
}
