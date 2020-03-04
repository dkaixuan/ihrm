package com.ihrm.system.realm;

import com.ihrm.common.shiro.realm.IhrmRealm;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Permissions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/3/3 10:32
 * 配置用户登录认证的realm域，继承公共的IhrmRealm补充其中的认证方法
 */
public class UserRealm extends IhrmRealm {

    private static final String UserLevel="user";
    private static final String coAdminLevel = "coAdmin";
    /**
     *  0 企业不可见,1 企业可见
     */
    private static final String enVisible="enVisible";

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;


    @Override
    public void setName(String name) {
        super.setName("userRealm");
    }



    /**
     * 重写认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取用户的手机号和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //2.根据手机号查询用户
        String mobile = upToken.getUsername();
        String password = new String( upToken.getPassword());

        User user = userService.findByMobile(mobile);
        //3.判断用户是否存在，用户密码是否和输入密码一致
        if (user != null && user.getPassword().equals(password)) {
            //4.构造安全数据并返回（安全数据：用户基本数据，权限信息：profileResult)
            ProfileResult result=null;
            if (UserLevel.equals(user.getLevel())) {
                result = new ProfileResult(user);
            } else if (coAdminLevel.equals(user.getLevel())) {
                Map map = new HashMap();
                map.put(enVisible, "1");
                //所有权限
                List<Permission> permissionList = permissionService.findAll(map);
                result = new ProfileResult(user, permissionList);
            }
            //构造方法；安全数据，密码，realm名字
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(result,user.getPassword(),this.getName());
            return info;
        }

        //返回null，会抛出UnknownAccountException异常,全局异常处理捕获抛出用户名和密码错误提示
        return null;
    }
}
