package cn.tedu.seckill.mapper;

import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;

import java.util.List;

public interface SecMapper {
    List<Seckill> selectSeckills();

    Seckill selectOne(Long seckillId);

    int reduceNumber(long seckillId);

    void saveSuccess(Success success);
}
