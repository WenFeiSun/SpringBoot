package com.sun.control.web.common.annotation;


import java.lang.annotation.*;

/**
 * 自定义日志注解
 * @author sunwenfei
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    /** 要执行的操作类型比如：add操作 **/
    public String methodType() default "";
    /** 要执行的具体操作比如：添加用户 **/
    public String methodValue() default "";
}