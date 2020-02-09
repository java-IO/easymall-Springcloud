package cn.tedu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
//验证是否读取到了redis-test1.properties属性
@SpringBootApplication
@EnableEurekaClient
public class StarterConfigClient{
    @Value("${application}")
    private String name;
    public static void main( String[] args )

    {
        SpringApplication.run(StarterConfigClient.class,args);
        System.out.println( "Hello World!" );
    }
}
