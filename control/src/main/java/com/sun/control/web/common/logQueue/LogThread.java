package com.sun.control.web.common.logQueue;


import com.sun.control.web.common.entity.SystemLog;
import com.sun.control.web.service.LogService;
import org.springframework.stereotype.Component;


/**
 * 日志线程类,将日志保存到队列中
 */
@Component
public class LogThread implements Runnable {

    private LogService logService;

    private SystemLog systemLog;

    public LogThread() {
    }

    public  LogThread(SystemLog systemLog, LogService logService) {
        this.systemLog = systemLog;
        this.logService = logService;
    }

    @Override
    public void run() {
        try {
            logService.insertOperlog(systemLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
