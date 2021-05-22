package com.example.xunyou.test;

public class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread()+"  run()方法执行");
    }
}
