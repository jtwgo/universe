package com.jtw.main.test.threadtest;

import java.util.concurrent.*;

public class Thread1 {

    private static int count = 0;
    private static final ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    public static void main(String[] args) {
        Thread t =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("wait...");
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("中断...");
        System.out.println("中断前："+t.isInterrupted());
        t.interrupt();
        System.out.println("中断后："+t.isInterrupted());

    }
}
