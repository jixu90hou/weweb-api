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
            for (int i=100;i<1000;i++){
                String path1 = "/p1/"+i;
                client.setData().forPath(path1,String.valueOf(i+10).getBytes());

            }
      /*      String path1 = "/p1/9991";
            String path2 = "/p1/9992";

            // client.create().creatingParentsIfNeeded().forPath(path);
            client.setData().forPath(path1,"03999".getBytes());
            client.setData().forPath(path2,"03999".getBytes());*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void setData(String path){

    }
}
