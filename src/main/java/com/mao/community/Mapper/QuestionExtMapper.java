package com.mao.community.Mapper;

import com.mao.community.dto.QuestionDTO;
import com.mao.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);
}