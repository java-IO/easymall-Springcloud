package cn.tedu.user.mapper;

import com.jt.common.pojo.User;

public interface UserMapper {
    int selectUserExistByName(String userName);

    void insertUser(User user);

    User selectUserByNameAndPassword(User user);
}
