package com.sun.common.utils;

import com.sun.common.core.Result;

public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error( String msg) {
        Result result = new Result();
        result.setCode(1);
        result.setMsg(msg);
        return result;
    }
}
