package com.weweb.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class TestZkCuratorClient {
    private static String connectionInfo = "192.168.1.111:2181";

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectionInfo, new ExponentialBackoffRetry(1000, 3));
        try {
            client.start();
            String path = "/path";
           // client.create().creatingParentsIfNeeded().forPath(path);
            client.setData().forPath(path,"01".getBytes());
            client.setData().forPath(path,"03".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void setData(String path){

    }
}
