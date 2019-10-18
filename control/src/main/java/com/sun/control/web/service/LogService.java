package com.sun.control.web.service;

import com.sun.control.web.common.entity.SystemLog;

public interface LogService {
    void insertOperlog(SystemLog log);
}
