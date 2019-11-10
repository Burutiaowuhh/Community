package com.mao.community.controller;

import com.mao.community.dto.CommentDTO;
import com.mao.community.dto.ResultDTO;
import com.mao.community.exception.CustomizeErrorCode;
import com.mao.community.model.Comment;
import com.mao.community.model.User;
import com.mao.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody//返回的也是json格式
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,//spring将json反序列化成commentDTO对象
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        if (user == null) {
            return ResultDTO.errorof(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okof();
    }
}
