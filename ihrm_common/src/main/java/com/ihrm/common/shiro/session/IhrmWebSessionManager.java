package com.ihrm.common.shiro.session;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/3/3 10:14
 * 自定义会话管理器
 * 之前的程序使用jwt的方式进行用户认证，前端发送后端的是请求头中的token，为了适配之前的程序，
 * 在shiro中需要更改sessionId的获取方式。
 * 在shiro的会话管理中，可以使用请求头中的内容作为sessionId
 */
public class IhrmWebSessionManager extends DefaultWebSessionManager {
    private static final String AUTHORIZATION = "Authorization";
    private static final String HEADER = "header";

    public IhrmWebSessionManager() {
        super();
    }


    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(id)) {
            //如果没有携带id参数则按照父类的方式在cookie中获取
            return super.getSessionId(request, response);
        }else {
            //如果请求头中有authToken 则其值为sessionId
            id = id.replace("Bearer ", "");

                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,HEADER);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }

    }
}
