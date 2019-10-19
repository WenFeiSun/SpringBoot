package com.sun.control.web.test;

import com.sun.common.utils.RedisUitls;
import javax.annotation.Resource;
import java.util.Map;

public class TestShiro {

    //生成加密后的密码
    /*public static void main(String[] str){

        //加密方式
        String algorithmName="MD5";

        //加密的字符串
        String source="315e9fcd0009c33bbb65affd925a970d";

        //盐值，用于和密码混合起来用
        ByteSource salt = ByteSource.Util.bytes("abc123");

        //加密的次数,可以进行多次的加密操作
        int hashIterations = 2;

        //通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, source, salt, hashIterations);

        System.out.println(hash.toString());
    }*/
}
