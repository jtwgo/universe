package com.jtw.main.test.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler {
    private Person person;
    public Handler(Person person){
        this.person = person;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before run .....");
        method.invoke(this.person,args);
        System.out.println("after run .....");
        return null;
    }
}
