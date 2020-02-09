package cn.tedu;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class SentinelTest {
    /*
    获取哨兵集群连接节点信息
    从哨兵集群创建连接池,底层保存的连接资源 连接
    主节点的.
     */
    @Test
    public void sentinel(){
        //将哨兵集群节点信息收集
        Set<String> sentinels=new HashSet<>();
        sentinels.add(
                new HostAndPort("10.9.104.184",
                        26379).toString());
        sentinels.add(
                new HostAndPort("10.9.104.184",
                        26380).toString());
        sentinels.add(
                new HostAndPort("10.9.104.184",
                        26381).toString());
        //创建一个哨兵连接池
        //masterName:主从代号
        //set:哨兵集群节点信息
        JedisSentinelPool pool=new JedisSentinelPool(
                "mymaster",sentinels);
        //调用pool的getResource方法获取的
        // 是当前主从中主节点
        Jedis master = pool.getResource();
        master.set("master","haha");
        System.out.println("当前集群master:"+
                pool.getCurrentHostMaster());
    }
}
