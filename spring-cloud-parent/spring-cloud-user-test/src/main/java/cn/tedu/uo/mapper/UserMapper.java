package cn.tedu.uo.mapper;

import cn.tedu.uo.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 增加指定id用户积分
     * @param userId 要增加积分的用户id
     * @param point 要增加的积分数量
     */
    void updatePoint(@Param("userId") String userId, @Param("point") int point);

    /**
     * 根据用户编号查询用户
     * @param userId 要查询的用户编号
     */
    User queryUserById(String userId);
}
