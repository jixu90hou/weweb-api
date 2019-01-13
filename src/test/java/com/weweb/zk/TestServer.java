package com.weweb.zk;

import org.apache.curator.framework.recipes.cache.NodeCache;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestServer {
    public static String ZK_ADDR = "192.168.1.111:2181";
    public static String getCurrentDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return sdf.format(new Date());
    }
    public static void printData(NodeCache cache){
        System.out.println("节点数据：" + new String(cache.getCurrentData().getData())+"\t"+getCurrentDate());

    }
}
