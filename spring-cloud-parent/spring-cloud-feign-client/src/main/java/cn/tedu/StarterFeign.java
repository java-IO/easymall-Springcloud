package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
//feign客户端注解
@EnableFeignClients
public class StarterFeign
{
    public static void main( String[] args )
    {
        SpringApplication.run(StarterFeign.class,args);
        System.out.println( "Hello World!" );
    }
}
