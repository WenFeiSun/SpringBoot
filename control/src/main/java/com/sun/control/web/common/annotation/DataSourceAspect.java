package com.wondersgroup.resdir.framework.aspectj;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.wondersgroup.resdir.common.annotation.DataSource;
import com.wondersgroup.resdir.common.config.datasource.DynamicDataSourceContextHolder;
import com.wondersgroup.resdir.common.utils.StringUtils;

/**
 * 多数据源处理
 * 
 * @author wondersgroup
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.wondersgroup.resdir.common.annotation.DataSource)")
    public void dsPointCut()
    {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (StringUtils.isNotNull(dataSource)) {
            Object[] args = point.getArgs();
            if (args.length > 0 && args[0] != null) {
                String dataSourceId = args[0].toString();
                DynamicDataSourceContextHolder.setDataSourceType(dataSourceId);
            }
            //DynamicDataSourceContextHolder.setDataSourceType(datasource.value().name());
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
