package com.jtw.main.Proccess;

public class SimpleTaskProccess extends ProccessTask {

    private static final int dispatcher_time = 5;
    @Override
    public void execute() {
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                Thread.sleep(dispatcher_time*1000);
                System.out.println("从数据库查询待执行的任务！");
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
