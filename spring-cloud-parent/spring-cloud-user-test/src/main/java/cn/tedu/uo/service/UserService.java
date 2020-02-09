package cn.tedu.uo.service;

import cn.tedu.uo.domain.User;

public interface UserService {
    /**
     * 根据支付金额增加用户积分
     * @param userId 用户编号
     * @param money 支付金额
     */
    void addPoints(String userId, int money);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     */
    User queryUser(String userId);
}
