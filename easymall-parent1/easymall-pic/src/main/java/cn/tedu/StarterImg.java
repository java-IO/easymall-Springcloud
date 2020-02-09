package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class StarterImg
{
    public static void main( String[] args )
    {
        SpringApplication.run(StarterImg.class,args);
        System.out.println( "Hello World!" );
    }
}
