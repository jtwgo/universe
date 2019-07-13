package com.jtw.main.mybatis.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class SimpleListener  implements Watcher{

    static Stat stat = new Stat();
    static ZooKeeper zk ;
    public static void main(String[] args) {
        try {
            zk =  new ZooKeeper("192.168.31.153:2181", 5000, new SimpleListener());
            zk.getData("/sgroup/server001",true, stat);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDataChanged && event.getPath().startsWith("/sgroup"))
        {
            try {
                System.out.println("data changed................" + new String(zk.getData("/sgroup/server001",true, stat)));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
