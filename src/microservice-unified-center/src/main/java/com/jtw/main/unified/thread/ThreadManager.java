package com.jtw.main.unified.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager
{
    private ThreadPoolExecutor POOL;
    private ThreadManager()
    {
        POOL = new ThreadPoolExecutor(5,10,15, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(0xa));
    }
    private static ThreadManager INSTANCE = new ThreadManager();
    public static ThreadManager getInstance()
    {
        return INSTANCE;
    }

    public void addThread(Runnable task)
    {
        POOL.execute(task);
    }

    public void shutdown()
    {
        POOL.shutdown();
        try {
            if(!POOL.awaitTermination(2, TimeUnit.SECONDS))
            {
                POOL.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            POOL.shutdownNow();
        }
    }
}
