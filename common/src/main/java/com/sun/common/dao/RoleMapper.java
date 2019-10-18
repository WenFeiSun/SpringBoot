package com.sun.common.dao;


import com.sun.common.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    /**
     * 根据用户名拿出用户信息
     * @param userName
     * @return
     */
    User queryUserByUserName(@Param("userName") String userName);
}
