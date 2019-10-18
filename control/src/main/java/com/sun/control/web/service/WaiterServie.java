package com.sun.control.web.service;

import com.sun.common.entity.Waiter;
import java.util.Map;

public interface WaiterServie {
    /**
     * waiter表格查询
     * @param userName
     * @return
     */
    Map queryWaiterList(String userName, int page, int limit);

    /**
     * 根据身份证查用户
     * @param idCard
     * @return
     */
    Waiter selectWaiterByIdCard(String idCard);

    /**
     * 添加用户
     * @param waiter
     * @return
     */
    void saveWaiter(Waiter waiter);

    /**
     * 根据key删除对象
     * @param code
     */
    void deleteWaiter(String code);

    /**
     * 获取修改的数据
     * @param code
     * @return
     */
    Waiter queryUpdateWaiterByCode(String code);

    /**
     * 修改
     * @param waiter
     */
    void updateWaiter(Waiter waiter);
}
