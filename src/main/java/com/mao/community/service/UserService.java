package com.mao.community.service;

import com.mao.community.Mapper.UserMapper;
import com.mao.community.model.User;
import com.mao.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createorupadte(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
//        User dbuser = userMapper.findByAccountid(user.getAccountid());
        if (users.size() == 0) {
            //创建
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            User dbuser = users.get(0);
            User updateuser = new User();
            updateuser.setAvatarUrl(user.getAvatarUrl());
            updateuser.setGmtModified(System.currentTimeMillis());
            updateuser.setName(user.getName());
            updateuser.setToken(user.getToken());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateuser, userExample1);//更新：第一个参数是更新后的user，第二个参数是Userexample
//            userMapper.updateuser(dbuser);
        }
    }
}
