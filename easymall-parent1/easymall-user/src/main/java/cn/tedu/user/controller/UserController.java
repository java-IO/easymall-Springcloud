package cn.tedu.user.controller;

import cn.tedu.user.service.UserService;
import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user/manage")
public class UserController {
    @Autowired
    private UserService us;
    //用户名校验
    @RequestMapping("checkUserName")
    public SysResult checkNameExist(String userName){
        return us.checkNameExist(userName);
    }
    //表单注册提交
    @RequestMapping("save")
    public SysResult doRegister(User user){
        try{
            us.doRegister(user);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"注册失败",null);
        }
    }
    //实现登录功能
    @RequestMapping("login")
    public SysResult doLogin(User user,
         HttpServletRequest req, HttpServletResponse res){
        //判断ticket是否生成成功,有ticket 相当于
        //登录成功
        String ticket=us.doLogin(user);
        if("".equals(ticket)){
            //登录失败,没有生成redis的数据
            return SysResult.build(201,"你咋失败了",null);
        }else{
            //携带cookie request response对象
            CookieUtils.setCookie(req,res,"EM_TICKET",ticket);
            /*Cookie cookie=new Cookie("EM_TICKET");*/
            return SysResult.ok();
        }

    }
    //获取用户登录状态
    @RequestMapping("query/{ticket}")
    public SysResult getUserJson(@PathVariable
                                 String ticket){
        return us.getUserJson(ticket);
    }
}
