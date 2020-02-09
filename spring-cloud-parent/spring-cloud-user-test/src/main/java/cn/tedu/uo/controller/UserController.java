package cn.tedu.uo.controller;

import cn.tedu.uo.domain.User;
import cn.tedu.uo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService = null;

    /**
     * 增加用户积分
     */
    @RequestMapping("/addPoints.action")
    @ResponseBody
    public String addPoints(String userId,int money){
        try {
            userService.addPoints(userId,money);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 查询用户积分
     */
    @RequestMapping("/queryPoints.action")
    @ResponseBody
    public User queryPoints(String userId){
        User user = userService.queryUser(userId);
        return user;
    }
}
