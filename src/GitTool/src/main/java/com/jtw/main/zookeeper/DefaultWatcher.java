package com.jtw.main.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class DefaultWatcher implements Watcher
{

    private ZooKeeper zooKeeper;

    public DefaultWatcher()
    {

    }

    public DefaultWatcher(ZooKeeper zooKeeper)
    {
        this.zooKeeper = zooKeeper;
    }
    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void process(WatchedEvent watchedEvent)
    {
        String path = watchedEvent.getPath();
        Event.EventType type = watchedEvent.getType();
        Event.KeeperState state = watchedEvent.getState();
        System.out.println("默认watcher监听:" + path + "\t" + type + "\t" + state);
        try {
            if (path != null)
            {
                Stat stat = zooKeeper.exists(path, true);
                zooKeeper.getData(path,true,stat);
                zooKeeper.getChildren(path,true);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
