package com.mao.community.controller;

import com.mao.community.dto.AccesstokenDTO;
import com.mao.community.dto.GithubUser;
import com.mao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){

        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id("3daee8956728aea3117c");
        accesstokenDTO.setClient_secret("31ddd76e4085d6098e5cf04f5af709dbee8329c1");
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accesstokenDTO.setState(state);
        String s=githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(s);
//        System.out.println(githubUser.getName());
        return "redirect:/";
    }
}
