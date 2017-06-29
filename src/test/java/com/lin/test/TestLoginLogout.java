package com.lin.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lwb on 2017-6-29.
 */
public class TestLoginLogout {
    @Test
    public void testHelloWorld(){
        //1、获取SecurityManager工厂，此处使用ini文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm1.ini");

        //得到SecurityManger实例，并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        //3、得到subject及创建 用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("1zhang","123");

        try {
            //4、登录、即身份验证
            subject.login(token);
        }catch (Exception e){
            //5、身份验证失败
            String hello = "hello";
            System.out.println("hello");
        }
        Assert.assertEquals(true,subject.isAuthenticated());//断言用户已经登录
        //6、退出
        subject.logout();

    }

    @Test
    public void testJdbcRealm(){
        //1、获取SecurityManager工厂，此处使用ini文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");

        //得到SecurityManger实例，并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        //3、得到subject及创建 用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

        try {
            //4、登录、即身份验证
            subject.login(token);
        }catch (Exception e){
            //5、身份验证失败
            String hello = "hello";
            System.out.println("hello");
        }
        Assert.assertEquals(true,subject.isAuthenticated());//断言用户已经登录
        //6、退出
        subject.logout();
    }

    @Test
    public void testAllSuccessfulStrategyWithSuccess(){
        login("classpath:shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合、其中包含了Realm验证成功的身份信息
        PrincipalCollection principals = subject.getPrincipals();

        Assert.assertEquals(2,principals.asList().size());

    }
    private void login(String configFile){
        //1、获取SecurityManager工厂，此处使用ini文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

        //得到SecurityManger实例，并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        //3、得到subject及创建 用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

        try {
            //4、登录、即身份验证
            subject.login(token);
        }catch (Exception e){
            //5、身份验证失败
            String hello = "hello";
            System.out.println("hello");
        }
    }

}
