package cn.tedu.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix="redis.cluster")
public class ClusterConfig {
    private List<String> nodes;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;
    //初始化JedisCluster
    @Bean
    public JedisCluster initCluster(){
        //收集节点信息
        Set<HostAndPort> set=new HashSet<>();
        for(String node:nodes){
            String host=node.split(":")[0];
            int port=Integer.parseInt(node.split(":")[1]);
            set.add(new HostAndPort(host,port));
        }
        //底层连接池属性
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        return new JedisCluster(set,config);
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
