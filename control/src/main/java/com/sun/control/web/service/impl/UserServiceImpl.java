package com.sun.control.web.service.impl;

import com.sun.common.dao.UserMapper;
import com.sun.common.entity.User;
import com.sun.control.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User queryUserByUserNameAndPwd(String userName,String password) {
        return userMapper.queryUserByUserNameAndPwd(userName,password);
        //return new User();
    }
}
