package cn.tedu;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * hash取余算法的封装
 * 实现所有jedis方法的重写,完成包装使用
 */
public class MyShardJedis {
    private List<Jedis> jedisList;
    //["10.9.104.184:6379","10.9.104.184:6380","10.9.104.184:6381"]
    public MyShardJedis(List<String> nodes){
        //使用nodes数据封装一个List<Jedis>
        jedisList=new ArrayList<Jedis>();
        for(String node:nodes){
            //node="10.9.104.184:6379"
            String host=node.split(":")[0];
            int port=Integer.parseInt(node.split(":")[1]);
            jedisList.add(new Jedis(host,port));
        }
    }
    //实现分片计算逻辑
    public Jedis computeShard(String key){
        int result=(key.hashCode()&Integer.MAX_VALUE)%jedisList.size();
        return jedisList.get(result);
    }

    //封装大量的方法 get,set,exists,expire,等 jedis的api全部重写一遍
    public String get(String key){
        Jedis j=computeShard(key);
        return j.get(key);
    }
    public void set(String key,String value){
        //向获取该key值对应jedis
        Jedis j=computeShard(key);
        j.set(key,value);
    }

}
