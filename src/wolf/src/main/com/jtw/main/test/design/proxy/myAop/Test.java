package com.jtw.main.test.design.proxy.myAop;

import com.jtw.main.test.design.proxy.cglib.Cat;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Test {
    private static final String S = getString()+"..";
    private static final String s1 = "hello"+"world";
    private static final String s2 = "he"+"llo"+"world".length();
    public static void main(String[] args) throws ClassNotFoundException {
//        MyProxy proxy = (MyProxy) new MyProxy(Cat.class,new MyHandler1()).getProxy();
//        proxy.invoke(null);
//            getString();
        System.out.println(s1==s2);
        short a = 1;
        a = (short)(5-a);
    }
    public static String test() throws ClassNotFoundException {
        //获取整个类
        Class c = Class.forName("java.lang.Integer");
        //获取所有的属性?
        Field[] fs = c.getDeclaredFields();

        //定义可变长的字符串，用来存储属性
        StringBuffer sb = new StringBuffer();
        //通过追加的方法，将每个属性拼接到此字符串中
        //最外边的public定义
        sb.append(Modifier.toString(c.getModifiers()) + " class " + c.getSimpleName() +"{\n");
        //里边的每一个属性
        for(Field field:fs){
            sb.append("\t");//空格
            sb.append(Modifier.toString(field.getModifiers())+" ");//获得属性的修饰符，例如public，static等等
            sb.append(field.getType().getSimpleName() + " ");//属性的类型的名字
            sb.append(field.getName()+";\n");//属性的名字+回车
        }

        sb.append("}");

        System.out.println(sb);
        return sb.toString();
    }
    public static String getString(){
        Class<?> clazz = String.class;
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        clazz.getModifiers();
        sb.append(Modifier.toString(clazz.getModifiers())+" class "+clazz.getSimpleName()+"{\n");
        for (Field field:fields
             ) {
            sb.append("\t "+Modifier.toString(field.getModifiers()));
            sb.append(" "+field.getName());
            sb.append(";\n");
        }
        sb.append("}\n");
//        System.out.println(sb.toString());
        return sb.toString();
    }
}
