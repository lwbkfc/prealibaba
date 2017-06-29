package com.lin.test;

import com.lin.alibaba.util.HttpClientUtil;
import com.lin.alibaba.util.HttpResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lwb on 2017/6/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestMultiThreadController {

    @Test
    public void testHello(){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while (true){
                    HttpResult httpResult = HttpClientUtil.get("http://localhost:8080/hello");
                    System.out.println("t1:"+httpResult.getContent());
                }
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                while (true){
                    HttpResult httpResult = HttpClientUtil.get("http://localhost:8080/hello");
                    System.out.println("t2:"+httpResult.getContent());
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
