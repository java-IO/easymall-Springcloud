package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.seckill.mapper")
public class StarterSeckill
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(StarterSeckill.class,args);
    }
    //定义声明的队列和交换机,绑定关系 direct
    @Bean
    public Queue queue01(){
        //初始化方法,初始化一个需要的队列组件
        return new Queue("seckillQ");
    }
    //声明交换机
    @Bean
    public DirectExchange ex01(){
        return new DirectExchange("seckillEX");
    }
    //绑定,使用路由key seckill
    @Bean
    public Binding bind01(){
        return BindingBuilder.bind(queue01()).to(ex01())
                .with("seckill");
    }
}
