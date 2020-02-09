package cn.tedu.controller;

import cn.tedu.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    private HiService hs;
    //访问ribbon地址 localhost:9005/hello?name=wang
    //假设，ribbon不会hello world 9001/9002会，
    @RequestMapping("/hello")
    public String sayHi(String name){
        return "RIBBON:"+hs.sayHi(name);
    }
}
