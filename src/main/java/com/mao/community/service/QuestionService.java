package com.mao.community.service;

import com.mao.community.Mapper.QuestionMapper;
import com.mao.community.Mapper.UserMapper;
import com.mao.community.dto.PaginationDTO;
import com.mao.community.dto.QuestionDTO;
import com.mao.community.model.Question;
import com.mao.community.model.User;
import org.apache.ibatis.annotations.Param;
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

    public PaginationDTO list(Integer page,Integer size)
    {
        PaginationDTO paginationDTO=new PaginationDTO();
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        Integer totalcount=questionMapper.findtotalCOunt();
        paginationDTO.setPagination(totalcount,page,size);


        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalpage()){
            page=paginationDTO.getTotalpage();
        }

        Integer offset=size*(page-1);
        List<Question> questionList = questionMapper.list(offset,size);

        for (Question question : questionList) {
           User user= userMapper.findByid(question.getCreator());
           QuestionDTO questionDTO=new QuestionDTO();
           BeanUtils.copyProperties(question,questionDTO);
           questionDTO.setUser(user);
           questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
