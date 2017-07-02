package com.lin.alibaba.shiro.config;

import com.lin.alibaba.shiro.MyRealm1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lwb on 2017-6-30.
 */
@Configuration
public class ShiroConfig {
    @Bean
    public MyRealm1 getMyRealm1(){
        return new MyRealm1();
    }

    @Bean
    public String printRealm(MyRealm1 myRealm1){
        System.out.println(myRealm1.getName());
        System.out.println("hello world");
        return "";
    }
}
