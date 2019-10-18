package com.sun.control.web.common.annotation;

import com.sun.control.web.common.entity.SystemLog;
import com.sun.control.web.common.logQueue.LogQueue;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * 日志记录类
 */
@Aspect
@Component
public class SystemLogAspect {
    //1.定义controller切点
    @Pointcut("@annotation(com.sun.control.web.common.annotation.SysLog)")
    public void controllerAspect() {
    }

    @AfterReturning(returning="ret", pointcut="controllerAspect()")
    public void afterReturn(JoinPoint joinPoint, Object ret) throws Throwable{
        //保存日志
        saveSysLog(joinPoint,ret);
    }
    /**\
     * /日志处理方法
     * @param joinPoint
     * @param
     */
    private void saveSysLog(JoinPoint joinPoint,Object ret) {
        try{
            // 获取当前操作的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            SysLog syslog = method.getAnnotation(SysLog.class);
            SystemLog log = new SystemLog();
            //请求的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            log.setId(UUID.randomUUID().toString().replace("-",""));
            //set方法名
            Object[] args = joinPoint.getArgs();
            //方法名
            log.setMethod(methodName);
            // 获取 ip 地址
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //set地址IP
            log.setRequestIp(request.getRemoteAddr());
            log.setDescription(syslog.methodValue());
            log.setLogType(syslog.methodType());

            //将任务添加到队列中去
            BlockingQueue<SystemLog> blockingQueue = LogQueue.blockingQueue;
            blockingQueue.put(log);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
