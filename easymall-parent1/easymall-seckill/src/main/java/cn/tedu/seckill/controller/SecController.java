package cn.tedu.seckill.controller;

import cn.tedu.seckill.mapper.SecMapper;
import com.jt.common.pojo.Seckill;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("seckill/manage")
public class SecController {
    @Autowired
    private SecMapper secMapper;
    //查询秒杀商品list
    @RequestMapping("list")
    public List<Seckill> queryAllSeckill(){
        //select * from seckill
        return secMapper.selectSeckills();
    }
    //返回单个秒杀商品的数据
    @RequestMapping("detail")
    public Seckill queryOne(Long seckillId){
        return secMapper.selectOne(seckillId);
    }
    @Autowired
    private RabbitTemplate template;
    @RequestMapping("send")
    public String send(String msg){
        //编写生产端逻辑 将消息发送给交换seckillEX
        //消息绑定路由key seckill
        //消息携带详细属性的发送
        /*MessageProperties prop=new MessageProperties();
        prop.setPriority(100);
        Message message=new Message(msg.getBytes(),prop);
        template.send("seckillEX",
                "seckill",message);*/
        //上述4行代码等价 channel.basicPublish(ex,routing,prop,byte[])
        //在这里发送消息时,是什么类型 Message 监听代码接收
        //简化版发送消息
        template.convertAndSend("seckillEX",
                "seckill",msg);
        return "success";
    }

    //发起秒杀
    @RequestMapping("{seckillId}")
    public SysResult startSeckill(@PathVariable Long seckillId){
        //准备消息
        String userPhone="1889090"+ RandomUtils.nextInt(9999);
        String msg=userPhone+"/"+seckillId;
        template.convertAndSend("seckillEX",
                "seckill",msg);

        return SysResult.ok();
    }
}
