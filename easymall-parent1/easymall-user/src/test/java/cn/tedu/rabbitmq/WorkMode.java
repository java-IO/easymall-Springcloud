package cn.tedu.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

public class WorkMode {

    private Channel channel;

    @Before
    public void init() throws Exception {
        //构建一个场链接的连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //连接属性ip port user password virtrual host
        factory.setHost("10.9.39.13");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        channel=conn.createChannel();
    }
    //生产端
    @Test
    public void productor() throws Exception {
        //将消息生成 String msg
        channel.queueDeclare("workQ01",false,false,false,null);
        for (int i=0;i<100;i++){
            String msg="hello work"+i;
            channel.basicPublish
                    ("","workQ01",null,msg.getBytes());
            System.out.println("生产端发送消息第:"+i+"条");
        }

    }
    //消费端代码逻辑
    @Test
    public void consumer01() throws Exception {
        QueueingConsumer consumer=
                new QueueingConsumer(channel);
        //空闲时(确认返回之前 非空闲)最多可以接收 1条消息
        channel.basicQos(2);
        channel.basicConsume("workQ01",false,consumer);
        //执行消费逻辑
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//
            System.out.println("消费者1消费消息:"+new String(delivery.getBody()));
            //手动确认返回
            /*
            第一个参数:确认标识 每个delivery都有信封标识
            第二个参数:批量执行确认,true是,false只返回一个确认
             */
            //模拟处理消息消费逻辑的时间

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),
                    false);
        }
    }
    @Test
    public void consumer02() throws Exception {
        QueueingConsumer consumer=
                new QueueingConsumer(channel);
        //空闲时(确认返回之前 非空闲)最多可以接收 1条消息
        channel.basicQos(2);
        channel.basicConsume("workQ01",false,consumer);
        //执行消费逻辑
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//
            System.out.println("消费者2消费消息:"+new String(delivery.getBody()));
            //手动确认返回
            /*
            第一个参数:确认标识 每个delivery都有信封标识
            第二个参数:批量执行确认,true是,false只返回一个确认
             */
            //模拟处理消息消费逻辑的时间

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),
                    false);
        }
    }
}
