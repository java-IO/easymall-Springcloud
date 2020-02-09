package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
//cn.tedu.product.** cn.tedu.user.**
@SpringBootApplication
@MapperScan("cn.tedu.product.mapper")
@EnableEurekaClient
public class StarterProd
{
    public static void main( String[] args )
    {
        SpringApplication.run(StarterProd.class,args);
        System.out.println( "Hello World!" );
    }
}
