package com.jtw.main.test.day1.day2;

import java.util.ArrayList;
import java.util.List;

public class Person {
    static {
        System.out.println("father 静态代码块加载");
    }
    {
        System.out.println("father 构造代码块加载");
        father = "father 构造代码块";
    }
    public Person (){
        System.out.println("初始化father构造器");

    }
    private String father = "father";

    public static void main(String[] args) {
        System.out.println(new Person().father);;
        List<String> list = new ArrayList<String>();
        list.add("");
    }

}
