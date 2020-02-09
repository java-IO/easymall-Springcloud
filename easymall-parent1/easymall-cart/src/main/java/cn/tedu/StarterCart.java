package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.cart.mapper")
public class StarterCart
{
    public static void main( String[] args )
    {
        SpringApplication.run(StarterCart.class,args);
        System.out.println( "Hello World!" );
    }
    //负载均衡的对象RestTemplate
    @Bean
    @LoadBalanced
    public RestTemplate initTemplate(){
        return new RestTemplate();
    }
}
