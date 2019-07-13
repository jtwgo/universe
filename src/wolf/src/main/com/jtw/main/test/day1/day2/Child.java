package com.jtw.main.test.day1.day2;

public class Child extends Person {
    static {
        System.out.println("child 静态代码块");
    }
    {
        System.out.println("child 构造代码块");
    }
    public Child(){
        super();
        System.out.println("child 构造器");
    }

    public static Child getInstance() {
        return null;
    }

    public static void main(String[] args) {
        new Child();
    }

}
