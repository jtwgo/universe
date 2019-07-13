package com.jtw.main.test;

import com.jtw.main.utils.PasswordUtils;

import java.util.UUID;

public class MainTest {


    public static void main(String[] args){
        Proxy proxy = new Proxy(new RealMan("小红"));
        proxy.sendCar();
        proxy.sendFlower();
        proxy.sendWatch();
        new Thread(()-> System.out.println("hello wolrd")).start();

    }

}
