package com.lin.alibaba.design.cap1;

/**
 * Created by Administrator on 2017-7-14.
 */
public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("I can't fly");
    }
}
