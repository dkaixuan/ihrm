package com.ihrm.common.interceptor;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 846602483
 * @Date: 2019-8-5 22:28
 * @Version 1.0
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        //通过request获取请求token信息
        String authorization = request.getHeader("Authorization");
        //判断请求头信息是否为空，或者是否以Bearer开头
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")){
            //获取token数据
            String token = authorization.replace("Bearer ","");
            //解析token获取claims
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null){
                //通过claims获取到当前用户的可访问api权限字符串
                String apis = (String) claims.get("apis");//api-user-delete,api-user-update
                //通过Handler
                HandlerMethod handlerMethod = (HandlerMethod)handler;
                //获取接口上的requestmapping注解
                RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                //获取当前接口中的name属性
                String name = requestMapping.name();
                //判断当前的用户是否具有响应的请求权限
                if (apis.contains(name)){
                    request.setAttribute("user-claims",claims);
                    return true;
                }else {
                    throw new CommonException(ResultCode.UNAUTHORISE);
                }

            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }
}
