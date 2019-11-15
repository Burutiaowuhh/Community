package com.mao.community.controller;

import com.mao.community.dto.CommentDTO;
import com.mao.community.dto.QuestionDTO;
import com.mao.community.enums.CommentTypeEnum;
import com.mao.community.model.DefaultUser;
import com.mao.community.model.User;
import com.mao.community.service.CommentService;
import com.mao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           HttpServletRequest request,
                           Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            DefaultUser defaultUser=new DefaultUser();
            defaultUser.setName("未登录");
            defaultUser.setAvatarUrl("/img/default.jpg");
            List<CommentDTO> commentDTOs= commentService.ListByTargetId(id, CommentTypeEnum.QUESTION);
            QuestionDTO questionDTO = questionService.getQuesinfo(id);//根据id获取问题、用户信息
            model.addAttribute("questionDTO", questionDTO);
            model.addAttribute("user",defaultUser);
            model.addAttribute("commentDTOs",commentDTOs);
            return "question";
        }
        questionService.incView(id);   //更新阅读数
        List<CommentDTO> commentDTOs= commentService.ListByTargetId(id, CommentTypeEnum.QUESTION);
        QuestionDTO questionDTO = questionService.getQuesinfo(id);//根据id获取问题、用户信息
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("user",user);
        model.addAttribute("commentDTOs",commentDTOs);
        return "question";
    }
}
