package com.mao.community.controller;

import com.mao.community.dto.AccesstokenDTO;
import com.mao.community.dto.GithubUser;
import com.mao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientid;

    @Value("${github.client.secret}")
    private String clientsecret;

    @Value("${github.redirect.uri}")
    private String redirecturi;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){
//        https://github.com/login/oauth/authorize?client_id=3daee8956728aea3117c&redirect_uri=http://localhost:8887/callback&scope=user&state=1
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientid);
        accesstokenDTO.setClient_secret(clientsecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirecturi);
        accesstokenDTO.setState(state);
        String s=githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(s);
        if(githubUser!=null){
            //登录成功
            request.getSession().setAttribute("user",githubUser);
            System.out.println(request.getSession().toString());
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }

    }
}
