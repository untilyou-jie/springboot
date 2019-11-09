package com.jie.maven.firstspringboot.service;

import com.jie.maven.firstspringboot.mapper.UserMapper;
import com.jie.maven.firstspringboot.model.User;
import com.jie.maven.firstspringboot.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()==0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

        }else{


            User dbUser = new User();
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(users.get(0).getId());

            userMapper.updateByExampleSelective(dbUser,userExample1);
        }
    }
}
