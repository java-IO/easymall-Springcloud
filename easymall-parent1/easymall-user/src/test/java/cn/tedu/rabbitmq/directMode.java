package cn.tedu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

public class directMode {
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
    private static final String type="direct";
    //交换机名称
    private static final String EX=type+"ex";
    //队列,2个队列
    private static final String Q1=type+"q01";
    private static final String Q2=type+"q02";
    //生产端,交换机声明,队列声明,交换机与队列绑定
    @Test
    public void productor() throws Exception {

        channel.exchangeDeclare(EX,type);
        //声明队列
        channel.queueDeclare(Q1,false,false,false,null);
        channel.queueDeclare(Q2,false,false,false,null);

        channel.queueBind(Q1,EX,"gz");
        channel.queueBind(Q2,EX,"bj");
        //消息的发送
        String msg="hello "+type;
        channel.basicPublish(EX,"sh",null,msg.getBytes());
    }

    //消费端代码
}
