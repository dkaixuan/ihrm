package com.ihrm.common.controller;

import com.ihrm.domain.system.response.ProfileResult;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;


    /**
     * ModelAtribute 注解指的是在控制器方法执行之前执行的方法
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setRequestAndResponse(HttpServletRequest request,HttpServletResponse response) {
        this.request=request;
        this.response=response;
        this.companyId="1";
    }


}
