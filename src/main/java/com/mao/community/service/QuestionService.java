package com.mao.community.service;

import com.mao.community.Mapper.QuestionMapper;
import com.mao.community.Mapper.UserMapper;
import com.mao.community.dto.QuestionDTO;
import com.mao.community.model.Question;
import com.mao.community.model.User;
import org.apache.ibatis.annotations.Select;
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

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
           User user= userMapper.findByid(question.getCreator());
           QuestionDTO questionDTO=new QuestionDTO();
           BeanUtils.copyProperties(question,questionDTO);
           questionDTO.setUser(user);
           questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
