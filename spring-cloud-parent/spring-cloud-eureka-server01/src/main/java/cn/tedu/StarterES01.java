package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
//作为配置类，提供唯一的服务注册中心注解
//当前web应用一旦启动，会加入一个注册中心的进程
@EnableEurekaServer
public class StarterES01 {
    public static void main(String[] args) {
        SpringApplication.run(StarterES01.class,args);
    }
}
