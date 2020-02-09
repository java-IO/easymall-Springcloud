package cn.tedu.uo.mapper;

import cn.tedu.uo.domain.Order;

public interface OrderMapper {
    /**
     * 根据订单id查询订单信息
     * @param orderId 要查询的订单编号
     * @return 订单信息bean
     */
    Order queryOrder(String orderId);
}
