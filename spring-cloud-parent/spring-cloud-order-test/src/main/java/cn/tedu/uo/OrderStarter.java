package cn.tedu.uo;

import cn.tedu.uo.service.OrderService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("cn.tedu.uo.mapper")
@EnableEurekaClient
public class OrderStarter extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(OrderStarter.class);
    }
    @Bean
    @LoadBalanced
    public RestTemplate initR(){
        return new RestTemplate();
    }
}
