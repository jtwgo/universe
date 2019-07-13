package com.jtw.main.test.design.proxy.cglib;

public class CglibTest {
    public static void main(String[] args) {
        MyCatHandler myCatHandler = new MyCatHandler();
        Cat cat = Factory.getProxy(myCatHandler);
        cat.catchMouse();
    }
}
