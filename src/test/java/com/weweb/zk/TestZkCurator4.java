package com.weweb.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static com.weweb.zk.TestServer.ZK_ADDR;
import static com.weweb.zk.TestServer.printData;

public class TestZkCurator4 {

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDR, new ExponentialBackoffRetry(1000, 3));
        try {
            client.start();
            String path = "/path";
            NodeCache cache = new NodeCache(client, path);
            NodeCacheListener listener = () -> {
                ChildData data = cache.getCurrentData();
                if (data != null) {
                    printData(cache);
                } else {
                    System.out.println("节点已经被删除!");
                }
            };
            cache.getListenable().addListener(listener);
            cache.start();
            synchronized (TestZkCurator4.class){
                TestZkCurator4.class.wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
