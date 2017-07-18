package com.lin.alibaba.design.cap1;

/**
 * Created by Administrator on 2017-7-14.
 */
public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("squeak");
    }
}
