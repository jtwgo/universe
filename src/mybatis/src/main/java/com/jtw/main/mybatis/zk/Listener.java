package com.jtw.main.mybatis.zk;

//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooKeeper;
//
//import java.io.IOException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Listener implements Watcher {

    ZooKeeper zooKeeper;
    Stat stat = new Stat();
    static String groupNode = "sgroup";
    @Override
    public void process(WatchedEvent event) {
        System.out.println("监听到zookeeper事件-----eventType:"+event.getType()+",path:"+event.getPath());
        if (event.getType() == EventType.NodeChildrenChanged &&
                event.getPath().equals("/" + groupNode)){

                System.out.println("node changed");

        }
        if (event.getType() == EventType.NodeDataChanged &&
                event.getPath().startsWith("/" + groupNode)){
            System.out.println("data changed");
//            AppServerMonitor.ServerInfo serverInfo=serverList.get(serverNodePath);
//            if(null!=serverInfo){
                //获取每个子节点下关联的服务器负载的信息

                byte[] data= new byte[0];
                try {
                    data = zooKeeper.getData(event.getPath(), true, stat);
                    String loadBalance=new String(data,"utf-8");
//                    serverInfo.setLoadBalance(loadBalance);
//                    System.out.println("@@@更新了服务器的负载："+serverInfo);
                    System.out.println("------" + loadBalance);
//                    System.out.println("###更新服务器负载后，服务器列表信息："+serverList);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }


    public static void main(String[] args) throws IOException, InterruptedException {
        Listener listener = new Listener();
        listener.start();
        listener.handle();
    }

    private void handle()
    {
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void start()
    {

        try {
            zooKeeper = new ZooKeeper("192.168.31.61:2181", 5000, this);
            //查看要检测的服务器集群的根节点是否存在，如果不存在，则创建
            if(null==zooKeeper.exists("/"+groupNode, false)){
                zooKeeper.create("/"+groupNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            updateNodeList();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    private void updateNodeList() throws KeeperException, InterruptedException, UnsupportedEncodingException {
        List<String> subList=zooKeeper.getChildren("/"+groupNode,true);
        Map<String, AppServerMonitor.ServerInfo> newServerList = new HashMap<>();
        for (String subNode : subList)
        {
            AppServerMonitor.ServerInfo serverInfo=new AppServerMonitor.ServerInfo();
            serverInfo.setPath("/"+groupNode+"/"+subNode);
            serverInfo.setName(subNode);
            //获取每个子节点下关联的服务器负载的信息
            byte[] data=zooKeeper.getData(serverInfo.getPath(), true, stat);
            String loadBalance=new String(data,"utf-8");
            serverInfo.setLoadBalance(loadBalance);
            newServerList.put(serverInfo.getPath(), serverInfo);
        }
    }
}
