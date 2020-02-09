package cn.tedu.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleMode {
    //客户端连接rabbitmq需要创建连接对象
    //长连接,短连接
    private Channel channel;
    //Before初始化channel对象
    @Before
    public void init() throws Exception {
        //构建一个场链接的连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //连接属性ip port user password virtrual host
        factory.setHost("10.9.39.13");
        factory.setPort(5672);
        //guest guest /虚拟主机名称
        //从连接工厂获取一个连接对象
        Connection conn = factory.newConnection();
        //给私有属性赋值初始化
        channel=conn.createChannel();
    }
    //生产端
    @Test
    public void productor() throws Exception {
        //将消息生成 String msg
        String msg="大家好";
        //声明队列,绑定虚拟主机不同,数据不能互通,同一个虚拟主机
        //下只能存在一个名称资源 交换机 队列
        /*
            queue:String类型,表示队列名称,有则直接使用不再声明,无则创建
            durable:Boolean 表示队列是否支持持久化 true
            支持 false不支持,宕机重启,队列消失
            exclusive:Boolean,表示队列是否专属.true表示专属,
                只有声明队列的连接对象才能操作queue,false相反谁都態用
            autoDelete:Boolean,表示是否自动删除,true当最后一个监听的消费端断开连接时
            队列消失了 false则不消失.
            arguments:Map类型,定义了队列的属性.
                消息剩余时间
                队列空闲最长时间(队列超时时间)
                队列可以存放消息最大个数
                ...
         */
        channel.queueDeclare("simpleQ01",false,false,false,null);
        //发送消息
        /*
            exchange: 发送的交换机名称 (AMQP default) 没名 ""
            routingKey:消息携带的路由key 假设发送给一个叫做simpleQ01的队列
            props: BasicProperties类型的消息属性.
                类似在使用消息传送的各种机制中,携带附加信息
                例如,信封,邮票,寄信地址,收心地址,收货人
                属性的意义在于可以从生产端,发送一些控制消费端消费逻辑的值
                    priority:优先级数值,可以控制消费端消息逻辑顺序.
            body:消息byte[]数组
         */
        channel.basicPublish("","simpleQ01",null,msg.getBytes());
    }
    //消费端代码逻辑
    @Test
    public void consumer01() throws Exception {
        //实现消费者绑定,实现监听,实现消费逻辑使用(拿着消息打印)
        //创建一个rabbitmq客户端消费者对象
        QueueingConsumer consumer=
                new QueueingConsumer(channel);
        //这个内存对象将会通过channel具备连接rabbitmq的资格
        //绑定一个消费对象监听的队列,设置确认机制
        /*
            queue:消费端绑定的队列
            autoAck:是否自动返回确认,消息一旦监听接收,直接返回确认
                当关注的是消费处理速度时,不关心处理正确性,可以使用自动确认(秒杀)
                但是关注处理的可靠性,正确性,必须手动确认(正确执行消费逻辑才确认)
            consumer:绑定队列的消费端对象
         */
        channel.basicConsume("simpleQ01",true,consumer);
        //执行消费逻辑
        while(true){
            //当前连接不断开,频繁的调用监听方法
            //Delivery是一个消息队列封装消息的完整对象
            //包括消息体,消息头,消息属性都可以获取
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//
            AMQP.BasicProperties prop = delivery.getProperties();
            //使用属性实现消费逻辑
            System.out.println(prop.getAppId());
            System.out.println(prop.getPriority());
            //获取消息体byte[] 实现打印字符串
            System.out.println("消费者1接收消息:"+
                    new String(delivery.getBody()));
            /*if(prop.getPriority()<1000){//不重要的消息
                //延迟处理
            }*/
        }
    }
}
