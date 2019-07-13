package com.jtw.main.thread;

import sun.nio.ch.ThreadPool;

import java.util.List;
import java.util.concurrent.*;

public class ThreadManager {

    private ThreadManager(){}
    private static final ThreadManager INSTANCE = new ThreadManager();
    public static ThreadManager getInstance()
    {
        return INSTANCE;
    }
    private static final int THREAD_NUM = 10;
    private final ThreadPoolExecutor POOL = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_NUM);
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    public void schedule(Runnable task,long delay, long period)
    {
        scheduledExecutorService.scheduleAtFixedRate(task,delay,period, TimeUnit.SECONDS);


    }

    public void add(Runnable task)
    {
        POOL.execute(task);
    }

    public void execSouth(Runnable task)
    {
        singleThreadExecutor.execute(task);
    }
    public void destroy()
    {

        try
        {
            scheduledExecutorService.shutdown();
            System.out.println("scheduledExecutorService.shutdownNow...");
            if(!scheduledExecutorService.awaitTermination(2,TimeUnit.SECONDS))
            {
                System.out.println("scheduledExecutorService.shutdownNow");
                scheduledExecutorService.shutdownNow();
            }
        }
        catch (InterruptedException e)
        {
            scheduledExecutorService.shutdownNow();
        }

        try
        {
            POOL.shutdown();
            System.out.println("POOL.shutdownNow");
            if(!POOL.awaitTermination(2, TimeUnit.SECONDS))
            {
                System.out.println("POOL.shutdownNow");
                POOL.shutdownNow();
            }
        }
        catch (InterruptedException e)
        {
            if(!POOL.isShutdown())
            {
                System.out.println("POOL.shutdownNow");
                POOL.shutdownNow();
            }
        }

        try
        {
            singleThreadExecutor.shutdown();
            System.out.println("singleThreadExecutor.shutdown");
            if(!singleThreadExecutor.awaitTermination(2,TimeUnit.SECONDS))
            {
                singleThreadExecutor.shutdownNow();
                System.out.println("singleThreadExecutor.shutdownNow");
            }
        }
        catch (InterruptedException e)
        {
            if(!singleThreadExecutor.isShutdown())
            {
                System.out.println("singleThreadExecutor.shutdownNow");
                singleThreadExecutor.shutdownNow();
            }
        }

    }

    public Future sumit(Callable task) {
        if(task!=null)
        return  this.POOL.submit(task);
        return null;
    }
}
