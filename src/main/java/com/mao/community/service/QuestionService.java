package com.mao.community.service;

import com.mao.community.Mapper.QuestionExtMapper;
import com.mao.community.Mapper.QuestionMapper;
import com.mao.community.Mapper.UserMapper;
import com.mao.community.dto.PaginationDTO;
import com.mao.community.dto.QuestionDTO;
import com.mao.community.exception.CustomizeErrorCode;
import com.mao.community.exception.CustomizeException;
import com.mao.community.model.Question;
import com.mao.community.model.QuestionExample;
import com.mao.community.model.User;
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
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;


    public PaginationDTO list(Integer page, Integer size) {//用于获取首页index页面信息
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        Integer count = (int) questionMapper.countByExample(new QuestionExample());//获取问题总数
//        Integer totalcount = questionMapper.findtotalcount();
        paginationDTO.setPagination(count, page, size);  //用于获取页码信息

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalpage()) {
            page = paginationDTO.getTotalpage();
        }

        Integer offset = size * (page - 1);  //计算偏移量
        //分页sql语句
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
//        List<Question> questionList = questionMapper.list(offset, size);

        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
//            User user = userMapper.findByid(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO getquestionbyuserid(User user, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(user.getId());
        int totalcount = (int) questionMapper.countByExample(questionExample);
//        Integer totalcount = questionMapper.findtotalCOuntbyuser(user.getId());
        paginationDTO.setPagination(totalcount, page, size);//获取页码信息

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andCreatorEqualTo(user.getId());
        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample1, new RowBounds(offset, size));
//        List<Question> questionList = questionMapper.listbyuserid(user.getId(), offset, size);
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getQuesinfo(Long id) {      //根据问题id获取问题详细信息和用户详细信息，返回一个QuestionDTO对象

        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);

        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createorupdate(Question question) {  //问题的创建或更新
        if (question.getId() == null) {              //如果问题不存在
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
//            questionMapper.create(question);
        } else {
            //更新
            question.setGmtModified(question.getGmtCreate());
            Question updatequestion = new Question();
            updatequestion.setGmtModified(System.currentTimeMillis());
            updatequestion.setTitle(question.getTitle());
            updatequestion.setDescription(question.getDescription());
            updatequestion.setTag(question.getTag());
            //对问题进行更新
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(updatequestion, example);
            if (i != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
//            questionMapper.update(question);
        }
    }

    public void incView(Long id) {    //阅读数更新功能
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incView(record);   //利用自定义mapper实现阅读数更新功能
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        //将逗号换成|号，sql语句正则表达式
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));//格式形如java|spring|springmvc
        Question question=new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);   //相关问题列表
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
