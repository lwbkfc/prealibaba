package com.lin.alibaba;

/**
 * Created by lwb on 2017/6/23.
 */
public class StringTest {
    public static void main(String[] args) {

        final StringBuffer stringBuffer = new StringBuffer();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while (true){
                    int capacity = stringBuffer.capacity();
                    System.out.println("t1:"+capacity);
                }

            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                while (true){
                    int capacity = stringBuffer.capacity();
                    System.out.println("t2:"+capacity);
                }

            }
        },"t2");

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                while (true){
                    int capacity = stringBuffer.capacity();
                    System.out.println("t3:"+capacity);
                }

            }
        },"t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
