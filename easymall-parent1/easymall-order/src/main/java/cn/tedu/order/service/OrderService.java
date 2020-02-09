package cn.tedu.order.service;

import cn.tedu.order.mapper.OrderMapper;
import com.jt.common.pojo.Order;
import com.jt.common.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderMapper om;
    public void addOrder(Order order) {
        //补齐数据
        String orderId=UUID.randomUUID().toString();
        order.setOrderId(orderId);
        order.setOrderTime(new Date());
        order.setOrderPaystate(0);
        //利用持久层直接一次连接执行多条insert语句
        om.addOrder(order);
        /*//如果在业务层处理新增逻辑
        //先增加主表 insert into t_order
        om.addOrderData(order);
        //在增加子表
        for(OrderItem oi:order.getOrderItems()){
            //oi缺少orderId
            oi.setOrderId(orderId);
            om.addOrderItemData(oi);
        }*/
    }

    public List<Order> queryMyOrders(String userId) {
       return om.selectOrdersByUserId(userId);
        /*List<Order> oList=om.selectOrderByUserId(userId);
        //每个order对象缺少orderItems属性值
        for (Order o:oList){
           List<OrderItem> oItems= om.selectOrderItemsByOrderId(o.getOrderId());
           o.setOrderItems(oItems);
        }
        return oList;*/
    }

    public void deleteOrder(String orderId) {
        om.deleteOrder(orderId);
    }
}
