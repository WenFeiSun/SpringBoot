package com.sun.common.dao;

import com.sun.common.entity.Waiter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WaiterMapper {
    /**
     * 查table
     * @param userName
     * @return
     */
    List<Waiter> queryWaiterList(@Param("userName") String userName,@Param("startPage") Integer startPage,@Param("endPage") Integer endPage);

    /**
     * 查count
     * @param userName
     * @return
     */
    int queryWaiterListCount(@Param("userName") String userName);

    /**
     * 根据身份证获取用户
     * @param idCard
     * @return
     */
    Waiter selectWaiterByIdCard(@Param("idCard") String idCard);

    /**
     * 添加用户
     * @param waiter
     * @return
     */
    void saveWaiter(Waiter waiter);

    /**
     * 根据key删除
     * @param code
     */
    void deleteWaiter(@Param("code")String code);

    /**
     * 获取修改的数据
     * @param code
     * @return
     */
    Waiter queryUpdateWaiterByCode(@Param("code")String code);

    /**
     * 修改
     * @param waiter
     */
    void updateWaiter(Waiter waiter);
}
