package cn.tedu.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HiService {
    @Autowired
    private RestTemplate template;
    //sayHi是正常调用的访问服务的方法
    /*
    引入服务降级的注解命令注解
     */
    @HystrixCommand(fallbackMethod = "error")
    //error服务服务降级中,对于当前sayHi方法处理调用服务失败后
    //退而求其次的第二种方法,本类中采用方法相同的结构构造
    public String sayHi(String name) {
        //不具备说hello能力 9001/9002
        //默认负载均衡逻辑就是轮询
        //将service-hi解析，拿到key值
        return template.getForObject(
                "http://service-hi/hi?name="+name,String.class);
    }
    //一旦sayHi调用服务失败,断路器打开,都会采用error处理返回
    public String error(String name){
        return "sorry,error happened "+name;
    }
}
