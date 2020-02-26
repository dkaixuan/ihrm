package com.ihrm.common.handler;

import com.ihrm.common.entity.Result;
import com.ihrm.common.exception.CommonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 846602483
 * @Date: 2019-8-3 20:03
 * @Version 1.0
 * 全局异常处理
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result error(HttpServletRequest request, HttpServletResponse response,Exception e)throws IOException {
        e.printStackTrace();
        if (e.getClass() == CommonException.class){

            CommonException commonException = (CommonException) e;
            return new Result(commonException.getCode());


        }else {
            return Result.ERROR();
        }
    }
}
