package com.jtw.main.test.design.proxy.myAop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyProxy {
    private Class<?> clazz;
    private Handler handler;
    public MyProxy(Class<?> clazz,Handler handler)
    {
        this.clazz = clazz;
        this.handler = handler;
    }
    private Method[] getAllMethods(){

        return clazz.getDeclaredMethods();
    }
    public Object getProxy()
    {
        return this;
    }
    public void invoke(Object args){
        Method[] methods =getAllMethods();
        for (Method method:methods
             ) {
            try {
                this.handler.before();
                if (null != args)
                {
                    method.invoke(this.clazz.newInstance(),args);
                }
                else
                {
                    method.invoke(this.clazz.newInstance());
                }
                this.handler.after();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

    }
}
