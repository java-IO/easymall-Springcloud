package cn.tedu.uo.service;

import cn.tedu.uo.domain.User;
import cn.tedu.uo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public void addPoints(String userId, int money) {
        //1.根据金额计算积分
        int point = money * 1;
        //2.增加用户表中用户积分
        userMapper.updatePoint(userId,point);
    }

    @Override
    public User queryUser(String userId) {
        //根据用户编号查询用户信息并返回
        return userMapper.queryUserById(userId);
    }
}
