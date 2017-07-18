package com.lin.alibaba.design.cap1;

/**
 * Created by Administrator on 2017-7-14.
 */
public class ModelDuck extends Duck {
    public ModelDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Quack();
    }

    @Override
    public void display() {
        System.out.println("I'm a model duck");
    }

    @Override
    public void performFly() {
        super.performFly();
    }

    @Override
    public void performQuack() {
        super.performQuack();
    }

    @Override
    public void swim() {
        super.swim();
    }

    @Override
    public FlyBehavior getFlyBehavior() {
        return super.getFlyBehavior();
    }

    @Override
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        super.setFlyBehavior(flyBehavior);
    }

    @Override
    public QuackBehavior getQuackBehavior() {
        return super.getQuackBehavior();
    }

    @Override
    public void setQuackBehavior(QuackBehavior quackBehavior) {
        super.setQuackBehavior(quackBehavior);
    }
}
