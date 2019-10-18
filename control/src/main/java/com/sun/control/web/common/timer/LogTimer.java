package com.sun.control.web.common.timer;

import com.sun.control.web.common.entity.SystemLog;
import com.sun.control.web.common.logQueue.LogQueue;
import com.sun.control.web.common.logQueue.LogThread;
import com.sun.control.web.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
@EnableScheduling
public class LogTimer {
    @Autowired
    private LogService logService;
    @Scheduled(cron = "0/5 * * * * ? ") //调用时间
    public void saveLog(){
        System.out.println("我是定时器");
        /**
         * 定点循环执行队列中的任务
         */
        while(true){
            //获取到阻塞队列
            //获取到线程池
            ThreadPoolExecutor threadPool = LogQueue.executor;
            SystemLog entity = LogQueue.poll();
            //执行队列中的任务
            if(null != entity){
                threadPool.submit(new LogThread(entity,logService));
            }
        }
    }
}
