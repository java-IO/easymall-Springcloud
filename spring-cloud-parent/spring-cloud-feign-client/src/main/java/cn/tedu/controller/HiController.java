package cn.tedu.controller;

import cn.tedu.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    private HiService hs;
    //请求 feign工程"/hello"
    @RequestMapping("hello")
    public String sayHi(String name){
        //feign也不具备说英文 hello 9001/9002 service-hi
        return "FEIGN:"+hs.sayHi(name);
    }
}
