package com.jtw.test;

import com.jtw.main.po.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest
{

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object user = context.getBean("user");
        if (user instanceof User)
        {
            User user1 = (User)user;
            System.out.println("user info 信息");
            System.out.println(user1.getId());
        }
    }

}
