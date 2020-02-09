package cn.tedu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController//相当于同时使用Controller 和ResponseBody
public class HiController {
    @RequestMapping(value="hi",method = RequestMethod.GET)
    //@ResponseBody
    public String sayHi(@RequestParam("name") String name){
        return "hello world "+name+",i am from 9001";
    }
}
