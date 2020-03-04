package com.ihrm.system.config;

import com.ihrm.common.shiro.realm.IhrmRealm;
import com.ihrm.common.shiro.session.IhrmWebSessionManager;
import com.ihrm.system.realm.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/3/3 13:00
 */
@Configuration
public class ShiroConfig {


    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    //1.创建Realm
    @Bean
    public IhrmRealm getIhrmRealm() {
        IhrmRealm ihrmRealm = new UserRealm();
        return ihrmRealm;
    }

    //2.创建安全管理器
    @Bean
    public SecurityManager securityManager(IhrmRealm ihrmRealm) {
        //DefaultWebSecurityManager 默认安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义的会话管理器注册到安全管理器中,使用redis
        securityManager.setSessionManager(sessionManager());
        //自定义缓存实现,使用redis
        securityManager.setCacheManager(cacheManager());
        securityManager.setRealm(ihrmRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        //创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        //通用配置
        filterFactory.setLoginUrl("/autherror?code=1");
        filterFactory.setUnauthorizedUrl("/autherror?code=2");

        //设置过滤器集合
        Map<String, String> filterMap = new LinkedHashMap<>();
        //anon请求地址可以匿名访问
        filterMap.put("/sys/login", "anon");
        filterMap.put("/autherror", "anon");
        //authc请求地址必须认证(登录)之后可以访问
        filterMap.put("/**", "authc");
        //perms具有某种权限(使用注解配置授权)
        filterFactory.setFilterChainDefinitionMap(filterMap);
        return filterFactory;
    }

    /**
     * 配置shiro注解支持
     * @param
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor
    authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new
                AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * redis的控制器，操作redis
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        return redisManager;
    }


    /**
     * 缓存管理器
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDao
     *
     * @return
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 设置自定义会话管理器
     *
     * @return
     */
    public DefaultWebSessionManager sessionManager() {
        IhrmWebSessionManager sessionManager = new IhrmWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        //禁用cookie
        sessionManager.setSessionIdCookieEnabled(false);
        //禁用url重写
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }





}