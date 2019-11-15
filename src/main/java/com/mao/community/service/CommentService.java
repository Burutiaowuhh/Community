package com.mao.community.service;

import com.mao.community.Mapper.*;
import com.mao.community.dto.CommentDTO;
import com.mao.community.enums.CommentTypeEnum;
import com.mao.community.exception.CustomizeErrorCode;
import com.mao.community.exception.CustomizeException;
import com.mao.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Transactional  //事务处理 事务回滚 确保所有方法一起完成
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //更新评论的回复数
            Comment comment1 = new Comment();
            comment1.setId(comment.getParentId());
            comment1.setCommentCount(1);
            commentExtMapper.incCommentCount(comment1);

            commentMapper.insertSelective(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);//增加一条评论
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);//评论数更新功能
        }
    }

    public List<CommentDTO> ListByTargetId(Long id, CommentTypeEnum commentTypeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return null;
        } else {
            //获取去重的评论人id
//        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
//        //将评论人加到list里
//        List<Long> userIds=new ArrayList<>();
//        userIds.addAll(commentators);

            //获取去重的评论人id
            List<Long> userIds = comments.stream().map(comment -> comment.getCommentator()).distinct().collect(Collectors.toList());

            //查询所有评论人信息
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andIdIn(userIds);
            List<User> users = userMapper.selectByExample(userExample);
            //将评论人信息放到map里,形式如<userid,user>
            Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

            //将评论和用户信息绑定起来 comment-->commentDTO
            List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
                CommentDTO commentDTO = new CommentDTO();
                BeanUtils.copyProperties(comment, commentDTO);
                commentDTO.setUser(userMap.get(comment.getCommentator()));
                return commentDTO;
            }).collect(Collectors.toList());
            return commentDTOS;
        }
    }
}
