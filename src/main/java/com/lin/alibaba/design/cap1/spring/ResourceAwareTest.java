package com.lin.alibaba.design.cap1.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 * Created by Administrator on 2017-7-18.
 */
public class ResourceAwareTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");
        Resource res = ctx.getResource("book.xml");



    }
}
