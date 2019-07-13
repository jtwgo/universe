package com.jtw.main.mybatis.nio;

import java.nio.channels.SelectionKey;
import java.util.Set;

public class Monitor
{
    public static void main(String[] args) throws InterruptedException {
        while (true)
        {
            Set<SelectionKey> keys = MessageServer.selector.keys();
            System.out.println("当前所有的keys数量：" + keys.size());
            System.out.println("当前所有的keys:" + keys);
            Thread.sleep(2000);
        }
    }
}
