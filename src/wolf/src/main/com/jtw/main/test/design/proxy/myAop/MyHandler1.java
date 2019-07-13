package com.jtw.main.test.design.proxy.myAop;

import java.util.Date;

public class MyHandler1 implements Handler{
    public void before() {
        System.out.println("before time:"+new Date());
    }

    public void after() {
        System.out.println("after time :"+new Date());
    }
}
