package com.mao.community.controller;

import com.mao.community.Mapper.UserMapper;
import com.mao.community.dto.AccesstokenDTO;
import com.mao.community.dto.GithubUser;
import com.mao.community.model.User;
import com.mao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

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
            User user = new User();
            user.setAccountid(String.valueOf(githubUser.getId()));
            user.setUsername(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtcreate(System.currentTimeMillis());
            user.setGmtmodified(user.getGmtcreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            System.out.println(request.getSession().toString());
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }

    }
}
