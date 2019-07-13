package com.jtw.main.listeners;

import com.jtw.main.Proccess.HeartBeatManager;
import com.jtw.main.Proccess.SimpleTaskProccess;
import com.jtw.main.Proccess.SouthServiceTask;
import com.jtw.main.conf.PropertiesManager;
import com.jtw.main.ssl.SSLServer;
import com.jtw.main.thread.ThreadManager;
import com.jtw.main.utils.CommonUtils;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class InitListener extends ContextLoaderListener {

    private Properties sslProperties;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
//        //加载配置文件到内存中
//        loadConfig();
//        //从数据库中加载数据
//        loadDataFromDB();
//        //启动soket南向服务
//        startSouthService();
//        //启动任务线程
//        startTaskThread();

    }

    private void startTaskThread()
    {
        ThreadManager.getInstance().add(new SimpleTaskProccess());
        ThreadManager.getInstance().schedule(new HeartBeatManager(),10,10);
    }

    private void startSouthService()
    {
        System.out.println("启动南向socket服务");
        ThreadManager.getInstance().execSouth(new SouthServiceTask(SSLServer.getInstance()));

    }

    private void loadConfig()
    {
        System.out.println("加载配置文件到内存");
        PropertiesManager.getInstance().init();
        System.out.println(PropertiesManager.getInstance().getSslProperties().getProperty("ssl.port"));
    }

    private void loadDataFromDB()
    {
        System.out.println("从数据库中加载数据");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
//        //关闭socket服务
//        stopSouthService();
//        //关闭线程池
//        shutDownThreadPool();
    }

    private void shutDownThreadPool()
    {
        ThreadManager.getInstance().destroy();
    }

    private void stopSouthService()
    {
        SSLServer.getInstance().shutdown();
    }
}
