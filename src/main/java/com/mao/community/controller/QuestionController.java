package com.mao.community.controller;

import com.mao.community.dto.QuestionDTO;
import com.mao.community.model.User;
import com.mao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           HttpServletRequest request,
                           Model model) {

        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user.toString());
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            QuestionDTO questionDTO = questionService.getQuesinfo(id);//根据id获取问题、用户信息
            model.addAttribute("questionDTO", questionDTO);
            return "question";
        }
        questionService.incView(id);   //更新阅读数
        QuestionDTO questionDTO = questionService.getQuesinfo(id);//根据id获取问题、用户信息
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }
}
