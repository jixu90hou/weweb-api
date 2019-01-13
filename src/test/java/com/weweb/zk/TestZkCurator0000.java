package com.weweb.zk;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.weweb.zk.TestServer.ZK_ADDR;

public class TestZkCurator0000 {
    private static final Logger logger = LoggerFactory.getLogger(TestZkCurator0000.class);
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
            for (int i=0;i<10000;i++){
                String currentData="/p1/"+i;
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT).
                        forPath(currentData,String.valueOf(i).getBytes());
            }

            // client.delete()..forPath("/path");


        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }
}
