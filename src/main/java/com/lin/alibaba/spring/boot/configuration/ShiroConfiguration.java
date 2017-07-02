package com.lin.alibaba.spring.boot.configuration;

import com.lin.alibaba.spring.boot.filters.LoginAccessFilter;
import com.lin.alibaba.spring.boot.realm.UserRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-03.
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public UserRealm getUserRealm(){
        return new UserRealm();
    }
    /**
     * 安全管理器
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
//        manager.setRememberMeManager(rememberMeManager());
        manager.setSessionManager(getSessionManager());
        return manager;
    }
    @Bean
    public LoginAccessFilter getAccessFilter(){
        LoginAccessFilter filter = new LoginAccessFilter();
        return filter;
    }

    @Bean
    public SessionManager getSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Cookie cookie = new SimpleCookie("com_lin_alibaba_token");
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        bean.getFilters().put("accessFilter",new LoginAccessFilter());

        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();

//        filterChainDefinitionMap.put("/login.htm", "anon");//anon 可以理解为不拦截
//        filterChainDefinitionMap.put("/user-*", "anon");
//        filterChainDefinitionMap.put("/verify-code", "anon");
//        filterChainDefinitionMap.put("/passwd-forgot/**", "anon");
//        filterChainDefinitionMap.put("/log/**", "anon"); //接受数据信息
//        filterChainDefinitionMap.put("/api/**", "anon");

        //authc表示需要身份验证, 开发时候可以暂时注释
        filterChainDefinitionMap.put("/hello/**", "accessFilter");//user拦截器通过登录或者remember me也可以访问
//        filterChainDefinitionMap.put("/home/**", "user");
//        filterChainDefinitionMap.put("/goods/**", "user");
//        filterChainDefinitionMap.put("/sms/**", "user");
//        filterChainDefinitionMap.put("/rpt/**", "accessFilter");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
}
