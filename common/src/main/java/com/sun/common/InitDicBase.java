package com.sun.common;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 初始化字典项
 */
@Component
public class InitDicBase {
    @PostConstruct
    public void initDicBase(){
        System.out.println("服务启动既加载字典项--------------------");
    }
}
