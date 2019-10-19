package com.sun.control.web.common.annotation;

import com.sun.control.web.common.config.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源处理
 * 
 * @author sunwenfei
 */
@Aspect //作用是把当前类标识为一个切面供容器读取      切面（Aspect
@Order(1) //加载顺序    越小越提前加载
@Component
public class DataSourceAspect
{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Pointcut("@annotation(com.sun.control.web.common.annotation.DataSource)")    //@annotation是针对方法的注解   切入点（Pointcut）
    public void dsPointCut()
    {
    }
    @Around("dsPointCut()")//当需要改变目标方法的返回值时，只能使用Around方法；
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if (dataSource != null) {
            Object[] args = point.getArgs();
            if (args.length > 0 && args[0] != null) {
                String dataSourceId = args[0].toString();
                DynamicDataSourceContextHolder.setDataSourceType(dataSourceId);
            }
        }
        try
        {
            return point.proceed();
        }
        finally
        {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }
}
