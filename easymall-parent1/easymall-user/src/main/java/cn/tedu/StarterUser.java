package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.user.mapper")
public class StarterUser
{
    public static void main( String[] args )
    {
        SpringApplication.run(StarterUser.class,args);
      //  System.out.println("年后排");
    }
}
