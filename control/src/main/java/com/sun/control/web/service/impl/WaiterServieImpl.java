package com.sun.control.web.service.impl;

import com.sun.common.dao.WaiterMapper;
import com.sun.common.entity.Waiter;
import com.sun.control.web.service.WaiterServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WaiterServieImpl implements WaiterServie{
    @Autowired private WaiterMapper waiterMapper;
    @Override
    public Map queryWaiterList(String userName,int page,int limit) {
        int startPage = (page-1)*limit;
        int endPage = page*limit;
        int count = waiterMapper.queryWaiterListCount(userName);
        List<Waiter> waiterList = waiterMapper.queryWaiterList(userName,startPage,endPage);
        Map map = new HashMap();
        map.put("count",count);
        map.put("data",waiterList);
        return map;
    }

    @Override
    public Waiter selectWaiterByIdCard(String idCard) {
        return waiterMapper.selectWaiterByIdCard(idCard);
    }

    @Override
    public void saveWaiter(Waiter waiter) {
        waiterMapper.saveWaiter(waiter);
    }

    @Override
    public void deleteWaiter(String code) {
        waiterMapper.deleteWaiter(code);
    }

    @Override
    public Waiter queryUpdateWaiterByCode(String code) {
        return waiterMapper.queryUpdateWaiterByCode(code);
    }

    @Override
    public void updateWaiter(Waiter waiter) {
        waiterMapper.updateWaiter(waiter);
    }

}
