package cn.tedu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController//相当于同时使用Controller 和ResponseBody
public class HiController {
    @RequestMapping("hi")
    //@ResponseBody
    public String sayHi(String name){

        return "hello world "+name+",i am from 9003";
    }
}
