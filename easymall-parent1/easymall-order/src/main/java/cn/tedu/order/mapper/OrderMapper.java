package cn.tedu.order.mapper;

import com.jt.common.pojo.Order;

import java.util.List;

public interface OrderMapper {
    void addOrder(Order order);

    List<Order> selectOrdersByUserId(String userId);

    void deleteOrder(String orderId);
}
