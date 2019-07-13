package com.jtw.main.test.design.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class Factory {

    public static Cat getProxy(MethodInterceptor methodInterceptor){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Cat.class);
        enhancer.setCallback(methodInterceptor);
        Cat cat = (Cat)enhancer.create();
        return cat;
    }
}
