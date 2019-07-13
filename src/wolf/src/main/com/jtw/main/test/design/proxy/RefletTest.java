package com.jtw.main.test.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RefletTest {
    public static void main(String[] args) {
        Handler handler= new Handler(new Person() {
            public void run() {
                System.out.println("person is running..");
            }

            public void eat() {
                System.out.println("person is eating...");
            }
        });
        Object proxy = Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, handler);
        if (proxy instanceof Person)
        {
            ((Person)proxy).run();
            ((Person)proxy).eat();
        }
    }
}
