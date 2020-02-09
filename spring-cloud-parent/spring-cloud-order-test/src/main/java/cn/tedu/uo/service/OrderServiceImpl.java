package cn.tedu.uo.service;

import cn.tedu.uo.domain.Order;
import cn.tedu.uo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    /*@Value("${addPointsAddr}")
    private String addPointsAddr = null;*/

    @Autowired
    private OrderMapper orderMapper = null;
    @Autowired
    private RestTemplate template;
    @Override
    public void pay(String orderId) {
        //1.支付指定订单
        System.out.println("支付订单.."+orderId);
        //2.查询订单信息
        Order order = orderMapper.queryOrder(orderId);
        //--订单金额
        int money = order.getOrderMoney();
        //--用户编号
        String userId = order.getUserId();
        //3.基于金额计算积分
        int point = money * 1;
        String url="http://user-test/user/addPoints.action?userId="+userId+"&money="+money;
        //4.调用 远程用户服务 增加用户积分
        /*RestTemplate restTemplate = new RestTemplate();*/
        String result = template.getForObject(url,String.class);
        if("0".equals(result)){
            throw new RuntimeException("计算积分失败！");
        }
    }
}
