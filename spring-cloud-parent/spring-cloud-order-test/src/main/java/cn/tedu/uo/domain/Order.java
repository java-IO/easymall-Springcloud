package cn.tedu.uo.domain;

public class Order {
    private String orderId;
    private int orderMoney;
    private String userId;

    public Order() {
    }

    public Order(String orderId, int orderMoney, String userId) {
        this.orderId = orderId;
        this.orderMoney = orderMoney;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(int orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderMoney=" + orderMoney +
                ", userId='" + userId + '\'' +
                '}';
    }
}
