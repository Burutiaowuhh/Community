package com.mao.community.service;

import com.mao.community.Mapper.UserMapper;
import com.mao.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createorupadte(User user) {
       User dbuser= userMapper.findByAccountid(user.getAccountid());
       if(dbuser==null){
           //创建
           user.setGmtcreate(System.currentTimeMillis());
           user.setGmtmodified(user.getGmtcreate());
           userMapper.insert(user);
       }else {
           //更新
           dbuser.setAvatarUrl(user.getAvatarUrl());
           dbuser.setGmtmodified(System.currentTimeMillis());
           dbuser.setName(user.getName());
           dbuser.setToken(user.getToken());
           userMapper.updateuser(dbuser);
       }
    }
}
