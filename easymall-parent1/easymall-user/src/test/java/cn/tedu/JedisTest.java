package cn.tedu;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.*;

public class JedisTest {
    /*
    构造一个Jedis对象,使用这个对象
    可以操作,访问redis服务端节点
    这个Jedis 相当于一个在java代码
    中启动的redis-cli进程
     */
    @Test
    public void jedisCreate(){
        //host:redis服务端所在的服务器ip地址
        //port:redis节点占用的服务器端口
        Jedis jedis=new Jedis("10.9.104.184",6380);
        //有了对象可以执行api方法,发送命令操作redis
        //jedis.set("name","王老师");
        System.out.println(jedis.exists("name"));
        System.out.println(jedis.expire("name",200));
        System.out.println(jedis.ttl("name"));
        System.out.println(jedis.get("name"));
        /*jedis.hexists();
        jedis.hset();
        jedis.lpush();
        jedis.rpop();*/
    }
    /*
    使用hash取余算法,实现三个redis节点的数据分片计算
     */
    @Test
    public void hashQuyu(){
        Jedis jedis1=new Jedis("10.9.104.184",6379);
        Jedis jedis2=new Jedis("10.9.104.184",6380);
        Jedis jedis3=new Jedis("10.9.104.184",6381);
        /*"e2ae8852-9b19-4aa7-832a-18a69f874b41"*/
        //利用for循环,利用uuid 模拟不同客户端
        //生成的海量数据.
        for(int i=0;i<100;i++){
            String key= UUID.randomUUID().toString();
            String value="";
            //利用算法hash取余决定,这个key值应该
            //对应到哪个节点去做处理 set /get
            int result = (key.hashCode() & Integer.MAX_VALUE) % 3;
            if(result==0){//6379
                jedis1.set(key,value);
            }else if(result==1){//6380
                jedis2.set(key,value);
            }else if(result==2){//6381
                jedis3.set(key,value);
            }
        }
    }
    //验证hash取余单调性
    @Test
    public void dandiaoxing(){
        for(int i=0;i<10;i++){
            String key=UUID.randomUUID().toString();
            System.out.println(key.hashCode());
        }

       /* String key="e2ae8852-9b19-4aa7-832a-18a69f874b41";
        System.out.println((key.hashCode() & Integer.MAX_VALUE) % 3);*/
    }
    //自定义封装分片对象
    @Test
    public void myShard(){
        //收集节点信息
        List<String> nodes=new ArrayList<String>();
        nodes.add("10.9.104.184:6379");
        nodes.add("10.9.104.184:6380");
        nodes.add("10.9.104.184:6381");
        //创建MyShardeJedis
        MyShardJedis mj=new MyShardJedis(nodes);
        //该掉什么方法直接调用,重写了
        mj.set("location","wanglaoshi");
        System.out.println(mj.get("location"));
    }
    /*
    一致性hash算法,封装的对象shardedJedis
     */
    @Test
    public void consistentHash(){
        //构造分片对象 ShardedJedis 一个属性 nodes集群信息
        List<JedisShardInfo> info=new ArrayList<>();
        //6379 6380 6381详细信息
        info.add(new JedisShardInfo("10.9.104.184",6379,500,500,8));
        info.add(new JedisShardInfo("10.9.104.184",6380));
        info.add(new JedisShardInfo("10.9.104.184",6381));

        //构造分片对象
        ShardedJedis sj=new ShardedJedis(info);
        for(int i=0;i<1000;i++){
            sj.set(UUID.randomUUID().toString(),"");
            /*sj.set("key_"+i,"");*/
        }
        sj.close();
        /*//sj的api
        sj.set("age","18");
        System.out.println(sj.get("age"));*/
    }
    /*
    利用连接池,控制管理分片中的底层连接对象ShardedJedisPool
     */
    @Test
    public void shardedJedisPool(){
        //连接池底层创建的连接ShardedJedis
        //收集节点信息
        List<JedisShardInfo> info=new ArrayList<>();
        //6379 6380 6381详细信息
        info.add(new JedisShardInfo("10.9.104.184",6379));
        info.add(new JedisShardInfo("10.9.104.184",6380));
        info.add(new JedisShardInfo("10.9.104.184",6381));
        //配置连接池属性 最大连接数,最大空闲,最小空闲
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(10);
        config.setMinIdle(2);
        //构造连接词汇
        ShardedJedisPool pool=new ShardedJedisPool(config,info);
        //使用资源
        ShardedJedis sj = pool.getResource();
        //sj操作redis集群
        System.out.println(sj.ttl("f7dece3b-1547-4b5d-8cf9-12c619e52e52"));
    }
    //集群连接测试JedisCluster
    @Test
    public void jedisCluster(){
        //收集节点信息,最少可以只给一个节点,任意角色
        //因为对象在创建时会根据提供的这个节点直接获取
        //集群的所有节点状态,在配置时,为了防止提供的一个节点
        //出现网络波动,一般都会给多个 5个 6个
        Set<HostAndPort> sets=new HashSet<>();
        sets.add(new HostAndPort("10.9.104.184",8000));
        sets.add(new HostAndPort("10.9.104.184",8005));
        //底层自带连接池
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMinIdle(2);
        config.setMaxIdle(8);
        config.setMaxTotal(200);
        //构造这个jedisCluster对象
        JedisCluster cluster=new JedisCluster(sets,config);
        cluster.set("location","北京");
        System.out.println(cluster.get("location"));
    }

}
