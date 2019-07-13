package com.jtw.main.test;

import com.jtw.main.po.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class SpringTest
{

    public static void main(String[] args) throws InterruptedException {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Object user = context.getBean("user");
//        if (user instanceof User)
//        {
//            User user1 = (User)user;
//            System.out.println("user info 信息");
//            System.out.println(user1.getId());
//        }
//        BlockingQueue queue = new LinkedBlockingQueue();
        BlockingDeque queue = new LinkedBlockingDeque();
        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");
        queue.add("ddd");
        queue.add("eee");
//        System.out.println(queue.take());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.take());
        System.out.println(queue);
    }



}
