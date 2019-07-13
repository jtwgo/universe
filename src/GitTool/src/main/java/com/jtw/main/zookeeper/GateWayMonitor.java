package com.jtw.main.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class GateWayMonitor implements Watcher
{

    private static final String tmpPath = "/chengdu_region/az_1/gateway";

    private static ZooKeeper zooKeeper;

    private static Watcher monitorWatcher = new Watcher()
    {
        @Override
        public void process(WatchedEvent watchedEvent)
        {
            if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged)
            {
                try {
                    List<String> children = zooKeeper.getChildren(tmpPath, monitorWatcher);
                    System.out.println(children);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static void main(String[] args)
    {
        new GateWayMonitor().init();
    }

    public void init()
    {
        try
        {
            zooKeeper = new ZooKeeper("192.168.31.61:2181",2000,null);
            createPath(zooKeeper,tmpPath);
            Stat stat = zooKeeper.exists(tmpPath, false);
            if (stat != null)
            {
                zooKeeper.getChildren(tmpPath, monitorWatcher);
            }
            while (true)
            {
                synchronized (this)
                {
                    wait();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    private static void createPath(ZooKeeper zooKeeper, String tmpPath)
    {
        try
        {
            Stat stat = zooKeeper.exists(tmpPath, false);
            if (stat == null)
            {
                zooKeeper.create(tmpPath,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
        }
        catch (KeeperException  | InterruptedException e)
        {
            String parent = tmpPath.substring(0,tmpPath.lastIndexOf("/"));
            System.out.println(e.getMessage());
            createPath(zooKeeper, parent);
            try {
                zooKeeper.create(tmpPath,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void process(WatchedEvent watchedEvent)
    {

    }
}
