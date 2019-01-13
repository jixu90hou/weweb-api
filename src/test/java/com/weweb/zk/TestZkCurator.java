package com.weweb.zk;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.weweb.zk.TestServer.ZK_ADDR;
import static com.weweb.zk.TestServer.printData;

public class TestZkCurator {
    private static final Logger logger = LoggerFactory.getLogger(TestZkCurator.class);
    // Callable形式的Cache
    private static final Cache<String, String> CALLABLE_CACHE = CacheBuilder.newBuilder().
            expireAfterWrite(1, TimeUnit.HOURS).maximumSize(500).
            recordStats().
            removalListener((notification) ->
                    logger.info("Remove a map entry which key is {},value is {},cause is {}.",
                            notification.getKey(), notification.getValue(), notification.getCause().name())).build();

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDR, new ExponentialBackoffRetry(1000, 3));
        try {
            client.start();
            String path = "/path";
            // client.create().creatingParentsIfNeeded().forPath(path);
           /* client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT).
                    forPath("/path","张三".getBytes());*/
            // client.delete()..forPath("/path");
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
            synchronized (TestZkCurator.class) {
                TestZkCurator.class.wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
