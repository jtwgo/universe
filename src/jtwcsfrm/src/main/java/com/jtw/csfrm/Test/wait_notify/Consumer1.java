package com.jtw.csfrm.Test.wait_notify;

public class Consumer1 implements Runnable
{

    private final Resource resource;

    public Consumer1(Resource resource)
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
                    while (Resource.count <= 0)
                    {
                        System.out.println("Consumer wait......");
                        resource.wait();

                    }
                    System.out.println("consumer:" + Resource.count--);
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
