package cn.tedu;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class StarterRibbon
{
    public static void main( String[] args )
    {
        SpringApplication.run(StarterRibbon.class,args);
        System.out.println( "Hello World!" );
    }
    //@Bean 创建对象
    @Bean
    @LoadBalanced//ribbon提供的注解
    //一旦使用注解创建对象，发送http请求之前
    //会经过ribbon的拦截，实现调用服务的功能
    public RestTemplate initTemplate(){
        return new RestTemplate();
    }
    //自定义使用的IRule实现规则
   /* @Bean
    public IRule initRandomRule(){
        //new WeightedResponseTimeRule();
        return new RandomRule();
    }*/
}
