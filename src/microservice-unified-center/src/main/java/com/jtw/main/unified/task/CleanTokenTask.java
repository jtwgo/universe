package com.jtw.main.unified.task;

import com.jtw.main.unified.entity.Token;
import com.jtw.main.unified.manager.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class CleanTokenTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CleanTokenTask.class);
    private static final List<Token> expiredTokens = new LinkedList<>();
    public void run() {
        LOGGER.info("regular time clean invalid token task start...");
        while(!Thread.currentThread().isInterrupted())
        {
            try{
                //定期清理过期的token
                Thread.currentThread().sleep(5000);
                for(Token t : TokenManager.getInstance().queryAllToken())
                {
                    long createDate = t.getCreateDate();
                    long use = (System.currentTimeMillis()-createDate)/1000;
                    if(use>180)
                    {
                        //把过期的token挑选出来,统一删除
                        expiredTokens.add(t);
                    }
                }
                TokenManager.getInstance().removeAll(expiredTokens);
                expiredTokens.clear();
            }
            catch (InterruptedException e) {
                LOGGER.error(e.toString());
                Thread.currentThread().interrupt();
            }
        }
    }


}
