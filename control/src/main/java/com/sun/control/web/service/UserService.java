package com.sun.control.web.service;


import com.sun.common.entity.User;

public interface UserService {
    User queryUserByUserNameAndPwd(String userName,String password);
}
