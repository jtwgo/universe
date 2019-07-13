package com.jtw.csfrm.Test.wait_notify;

public class Main
{
    public static final Object LOCK = new Object();
    public static void main(String[] args)
    {
        Resource resource = new Resource();
        new Thread(new Producer1(resource)).start();
        new Thread(new Producer1(resource)).start();
        new Thread(new Producer1(resource)).start();
        new Thread(new Consumer1(resource)).start();
        new Thread(new Consumer1(resource)).start();
        new Thread(new Consumer1(resource)).start();
    }
}
