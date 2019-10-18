package com.sun.control.web.service.impl;

import com.sun.control.web.common.entity.SystemLog;
import com.sun.control.web.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Override
    public void insertOperlog(SystemLog log) {
        System.out.println(1);
    }
}
