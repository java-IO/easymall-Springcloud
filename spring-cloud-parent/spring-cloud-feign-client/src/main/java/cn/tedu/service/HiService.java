package cn.tedu.service;

import org.apache.catalina.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 添加各种注解,实现底层方法拼接
 * template.getForObject("http://service-hi/hi?name={name}" ,String.class)
 */
@FeignClient("service-hi")//有这个注解,将会动态代理实现所有抽象方法
//当前接口所有抽象方法实现逻辑,都是访问service-hi
//url=http://service-hi 搞定了
public interface HiService {
    //调用springmvc注解,实现后续拼接
    //http://service-hi/hi 请求方式getForObject
    @RequestMapping(value="hi",method = RequestMethod.GET)
    String sayHi(@RequestParam("name")String name);
    //template.postForObject(http://service-hi/haha?userId=#{},User.class)
    /*@RequestMapping(value="haha",method=RequestMethod.POST)
    User getUser(@RequestParam("userId") String userId);*/
}
