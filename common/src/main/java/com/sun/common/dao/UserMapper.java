package com.sun.common.dao;


import com.sun.common.entity.Role;
import com.sun.common.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper {
    /**
     * 根据用户名拿出用户信息
     * @param userName
     * @return
     */
    User queryUserByUserName(@Param("userName") String userName);/**
     * 根据用户名拿出用户信息
     * @param userId
     * @return
     */
    List<User> queryAuthListByUserName(@Param("userId") String userId);

    /**
     * 根据用户名
     * @param userName
     * @param password
     * @return
     */
    User queryUserByUserNameAndPwd(@Param("userName") String userName, @Param("pwd") String password);
}
