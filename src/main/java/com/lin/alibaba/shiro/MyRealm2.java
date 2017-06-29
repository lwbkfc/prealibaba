package com.lin.alibaba.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by lwb on 2017-6-29.
 */
public class MyRealm2 implements Realm {
    @Override
    public String getName() {
        return "myrealm2";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        if (!"wang".equals(username)){
            throw new UnknownAccountException();//如果用户名错误
        }
        if (!"123".equals(password)){
            throw new IncorrectCredentialsException();//如果密码错误
        }
        //如果身份验证成功，返回一个AuthenticationInfo实现
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
