package cn.tedu.uo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("cn.tedu.uo.mapper")
@EnableEurekaClient
public class UserStarter extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(UserStarter.class);
    }
}
