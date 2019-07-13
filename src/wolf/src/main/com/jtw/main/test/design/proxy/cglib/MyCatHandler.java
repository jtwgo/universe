package com.jtw.main.test.design.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCatHandler implements MethodInterceptor{

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before .........");
        methodProxy.invokeSuper(o,objects);
        System.out.println("after .........");
        return null;
    }

}
