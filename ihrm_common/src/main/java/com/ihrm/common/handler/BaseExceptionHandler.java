package com.ihrm.common.handler;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
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
@Slf4j
public class BaseExceptionHandler {

    /**
     * 异常处理方法
     * @param e
     * @return
     * @throws IOException
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e)throws IOException {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        if (e.getClass() == CommonException.class){
            CommonException commonException = (CommonException) e;
            return new Result(commonException.getCode());
        }else {
            return Result.ERROR();
        }
    }

    /**
     * 用户名或密码错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value =UnknownAccountException.class)
    public Result error(UnknownAccountException e) {
        log.error(ExceptionUtil.getMessage(e));
        return new Result(ResultCode.MOBILEORPASSWORDERROR);
    }


    /**
     * 权限不足
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public Result error(UnauthorizedException e) {
        log.error(ExceptionUtil.getMessage(e));
        return new Result(ResultCode.UNAUTHORISE);
    }







}
