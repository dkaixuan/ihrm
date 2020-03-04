package com.ihrm.common.controller;

import com.ihrm.domain.system.response.ProfileResult;
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


//    /**
//     * 使用JWT的方式获取
//     * ModelAtribute 注解指的是在控制器方法执行之前执行的方法
//     * @param request
//     * @param response
//     */
//    @ModelAttribute
//    public void setRequestAndResponse(HttpServletRequest request,HttpServletResponse response) {
//        this.request=request;
//        this.response=response;
//
//        Object user_claims = request.getAttribute("user_claims");
//        if (user_claims != null) {
//            this.claims = (Claims) user_claims;
//            this.companyId = (String) claims.get("companyId");
//            this.companyName = (String) claims.get("companyName");
//        }
//    }


    /**
     * ModelAtribute 注解指的是在控制器方法执行之前执行的方法
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setRequestAndResponse(HttpServletRequest request,HttpServletResponse response) {
        this.request=request;
        this.response=response;
        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //subject获取所有的安全数据集合
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null && !principals.isEmpty()) {
            //获取安全数据
            ProfileResult profileResult = (ProfileResult) principals.getPrimaryPrincipal();
            this.companyId = profileResult.getCompanyId();
            this.companyName = profileResult.getCompany();
        }
        }






}
