package cn.tedu.search.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.util.List;

@Configuration
@ConfigurationProperties("es")
public class EsConfig {
    private List<String> nodes;//负载均衡节点的连接数据ip:port
    private String clusterName;
    //初始化方法
    @Bean
    public TransportClient initTransportClient(){

        TransportClient client=
                new PreBuiltTransportClient(Settings.EMPTY);
        //解析nodes数据 10.9.39.13:9300,10.9.100.26;9300
        try{
            for(String node:nodes){
                String host=node.split(":")[0];
                int port=Integer.parseInt(node.split(":")[1]);
                //拿一个socket address
                InetSocketTransportAddress addr=
                    new InetSocketTransportAddress(
                        InetAddress.getByName(host),port);
                client.addTransportAddress(addr);
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return client;

    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
