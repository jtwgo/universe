package com.jtw.main.unified.listeners;

import com.jtw.main.unified.csfrm.dao.Server;
import com.jtw.main.unified.manager.TokenManager;
import com.jtw.main.unified.task.CleanTokenTask;
import com.jtw.main.unified.thread.ThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class ScheduleListener implements ServletContextListener
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleListener.class);



    public void contextInitialized(ServletContextEvent event) {
        LOGGER.error("unified is starting...");
        ThreadManager.getInstance().addThread(new CleanTokenTask());
        ThreadManager.getInstance().addThread(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Server().start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        LOGGER.error("unified-manager has start successfully.");
    }

    public void contextDestroyed(ServletContextEvent event) {
        TokenManager.getInstance().clearToken();
        ThreadManager.getInstance().shutdown();

    }
}
