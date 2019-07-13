package com.jtw.csfrm.Test.wait_notify;

import org.omg.PortableServer.THREAD_POLICY_ID;

public class Producer1 implements Runnable
{
    private static int cout = 0;

    private static final Object LOCK = new Object();
    private final Resource resource;

    public Producer1(Resource resource)
    {
        this.resource = resource;
    }
    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Thread.sleep(1);
                synchronized (resource)
                {
                    while (Resource.count >= 20)
                    {
                        System.out.println(Thread.currentThread().getName() + "-- Producer wait......");
                        resource.wait();
                    }

                    System.out.println(Thread.currentThread().getName() + "****producer produce：" + Resource.count++ );
                    resource.notifyAll();
                }
            }

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
