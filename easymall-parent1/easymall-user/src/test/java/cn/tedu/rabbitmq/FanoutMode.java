package cn.tedu.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 实现发布定于群发的功能
 */
public class FanoutMode {
        //获取初始化连接对象
    private Channel channel;
    @Before
    public void init() throws Exception{
        ConnectionFactory factory=new ConnectionFactory();
        //guest guest /
        factory.setHost("10.9.39.13");
        factory.setPort(5672);
        //拿到连接
        Connection connection = factory.newConnection();
        //channel赋值
        channel=connection.createChannel();
    }
    //准备好4个常量
    //交换机类型 type:direct fanout topic
    private static final String type="fanout";
    //交换机名称
    private static final String EX=type+"ex";
    //队列,2个队列
    private static final String Q1=type+"q01";
    private static final String Q2=type+"q02";
    //生产端,交换机声明,队列声明,交换机与队列绑定
    @Test
    public void productor() throws Exception {
        //声明交换机,当前已存在,直接使用,不存在则创建
        /*
            交换机名称 exchange
            交换机类型 type
         */
        channel.exchangeDeclare(EX,type);
        //声明队列
        channel.queueDeclare(Q1,false,false,false,null);
        channel.queueDeclare(Q2,false,false,false,null);
        //队列与交换机绑定,发布订阅2个队列,多个队列使用相同的
        //路由key值 ""
        /*
            queue: queue名称
            exchange: 当前queue绑定的交换机名称
            routingKey: 绑定时的routingkey
         */
        channel.queueBind(Q1,EX,"");
        channel.queueBind(Q2,EX,"");
        //消息的发送
        String msg="hello "+type;
        channel.basicPublish(EX,"",null,msg.getBytes());
    }

    //消费端代码
}
