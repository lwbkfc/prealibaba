package com.lin.alibaba;

import com.lin.alibaba.util.HttpClientUtil;
import com.lin.alibaba.util.HttpResult;

/**
 * Created by lwb on 2017/6/24.
 */
public class Main {
    public static void main(String[] args) {
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
