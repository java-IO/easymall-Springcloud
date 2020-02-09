package cn.tedu.uo.service;

public interface OrderService {
    /**
     * 支付订单 并增加用户积分
     * @param orderId 要支付的订单编号
     */
    void pay(String orderId);
}
