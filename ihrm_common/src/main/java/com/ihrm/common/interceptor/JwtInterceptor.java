package com.ihrm.common.interceptor;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/3/1 18:54
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
                String token = authorization.replace("Bearer", "");
                Claims claims = jwtUtil.decode(token, null);
                if (claims != null) {
                    String apis = (String) claims.get("apis");
                    //得到注解名
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                    String name = requestMapping.name();
                    //判断是否符合赋予的权限
                    if (apis.contains(name)) {
                        request.setAttribute("user_claims", claims);
                    } else {
                        throw new CommonException(ResultCode.UNAUTHENTICATED);
                    }
                    return true;
                }
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }
}
