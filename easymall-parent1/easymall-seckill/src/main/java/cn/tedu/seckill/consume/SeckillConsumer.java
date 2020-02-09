package cn.tedu.seckill.consume;

import cn.tedu.seckill.mapper.SecMapper;
import com.jt.common.pojo.Success;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

@Component
public class SeckillConsumer {
    @Autowired
    private SecMapper secMapper;
    //注入jedisCluster
    @Autowired
    private JedisCluster cluster;
    //消费逻辑方法,方法的参数就是发送时的消息对象
    @RabbitListener(queues ="seckillQ")
    public void consume(String msg){
        //消费逻辑
        System.out.println("消费者接收到消息:"+msg);
        //解析 userPhone+"/"+seckillId
        long userPhone = Long.parseLong(msg.split("/")[0]);
        long seckillId = Long.parseLong(msg.split("/")[1]);
        //减库存之前 从cluster获取权限
        String component = cluster.rpop("list1");
        if(component==null){
            System.out.println("用户:"+userPhone+",秒杀:"+seckillId+"失败");
            return;//元素被前面的消费逻辑,消费者秒杀完毕
        }
        //执行减库存逻辑 number>0 时间和seckillId
        int result=secMapper.reduceNumber(seckillId);
        //判断成功失败 result==1成功
        if(result==0){
            //当前用户 秒杀失败的
            System.out.println("用户:"+userPhone+",秒杀:"+seckillId+"失败");
            return ;//停止
        }else{
            //result==1秒杀成功 封装success对象,入库保存
            Success success=new Success();
            success.setUserPhone(userPhone);
            success.setSeckillId(seckillId);
            success.setState(0);
            success.setCreateTime(new Date());
            secMapper.saveSuccess(success);
        }
    }
}
