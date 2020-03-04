package com.ihrm.common.shiro.realm;

import com.ihrm.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import java.util.Set;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/3/3 10:27
 * 公共自定义Realm授权，只处理授权数据，认证方法在登录模块补全
 */
public class IhrmRealm extends AuthorizingRealm {

    /**
     * api权限
     */
    private static final String apis = "apis";


    @Override
    public void setName(String name) {
        super.setName("ihrmRealm");
    }

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取安全数据
        ProfileResult profileResult = (ProfileResult) principalCollection.getPrimaryPrincipal();
        //2.获取权限信息，返回值
        Set<String> apiPerms = (Set<String>) profileResult.getRoles().get(apis);
        //3.构造权限数据，返回值
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(apiPerms);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
