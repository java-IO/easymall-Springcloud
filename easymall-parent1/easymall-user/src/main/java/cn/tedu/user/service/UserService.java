package cn.tedu.user.service;

import cn.tedu.user.mapper.UserMapper;
import com.jt.common.pojo.User;
import com.jt.common.utils.MD5Util;
import com.jt.common.utils.MapperUtil;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper um;
    public SysResult checkNameExist(String userName) {
        //通过查询结果 1/0 返回sysresult
        //select count(user_id) from t_user where user_name=#{userName}
        int exist=um.selectUserExistByName(userName);
        if(exist==1){//表示存在,不可用
            return SysResult.build(201,"已存在",null);
        }else{
            return SysResult.ok();//可用,不存在
        }
    }

    public void doRegister(User user) {
        //补齐数据
        user.setUserId(UUID.randomUUID().toString());
        //加密密码
        String md5Pas= MD5Util.md5(user.getUserPassword());
        user.setUserPassword(md5Pas);
        um.insertUser(user);
    }
    /*@Autowired
    private RedisUtils utils;*/
    @Autowired
    private JedisCluster jedis;
    public String doLogin(User user) {

        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        User exist=um.selectUserByNameAndPassword(user);
        String ticket="";

        //判断成功失败
        if(exist==null){
            //用户名密码错误
            return ticket;
        }else{
            //唯一用户的校验和顶替
            String onlyOneUserKey="login_"+exist.getUserId();
            //判断这个key值是否存在
            if(jedis.exists(onlyOneUserKey)){
                //存在则说明有人登陆过
                //直接删掉之前的ticket
                jedis.del(jedis.get(onlyOneUserKey));
            }
             try{
                //ticket赋值
                ticket="EM_TICKET"
                        +System.currentTimeMillis()
                        +user.getUserName();

                //将ticket存储在onlyOneUserKey被下一个顶替
             jedis.set(onlyOneUserKey,ticket);
                exist.setUserPassword(null);
                String userJson=
                        MapperUtil.MP.writeValueAsString(exist);

                jedis.setex(ticket,60*60*2,userJson);//set name value EX 200
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        }
        return ticket;
    }
    //实现续租
    public SysResult getUserJson(String ticket) {
        try{
            //判断剩余时间 pexpire和pttl 毫秒
            Long leftTime = jedis.pttl(ticket);
            //判断剩余时间是否小于30分钟
            if(leftTime<1000*60*30){
                //到达延长时间临界点,直接延长1小时
                Long expandTime=leftTime+1000*60*60;
                jedis.pexpire(ticket,expandTime);
            }
            String userJson = jedis.get(ticket);
            if(userJson==null){
                //浏览器有ticket值,redis已经超时删除数据
                return  SysResult.build(201,"可能超时",null);
            }else{
                //正常登录状态
                return SysResult.build(200,"获取成功",userJson);
            }
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"出现异常",null);
        }
    }
}
