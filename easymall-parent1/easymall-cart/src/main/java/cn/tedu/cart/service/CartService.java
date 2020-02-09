package cn.tedu.cart.service;

import cn.tedu.cart.mapper.CartMapper;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cm;
    public List<Cart> queryMyCart(String userId) {
        return cm.selectCartsByUserId(userId);
    }
    //调用其他微服务
    @Autowired
    private RestTemplate template;
    public void addCart(Cart cart) {
        /*
        1.查询数据库是否存在该购物车商品
        2.如果存在,update num 将旧数据和新数据num叠加
        3.如果不存在,需要调用商品获取数据
         */
        Cart exist=cm
            .selectExistCartByUserAndProduct(cart);
        //select * from t_cart where user_id and product_id
        if(exist!=null){
            //说明存在cart
            cart.setNum(cart.getNum()+exist.getNum());
            cm.updateCartNum(cart);
            //update t_cart set num=#{num} where user_id and product_id
        }else{
            //购物车数据不存在,新增insert
            //先要将数据补齐 product_name product-price product_image
            //调用商品单个查询微服务功能
            String url="http://productservice/product/manage/item/"+cart.getProductId();
            Product p= template.getForObject(url, Product.class);
            cart.setProductName(p.getProductName());
            cart.setProductImage(p.getProductImgurl());
            cart.setProductPrice(p.getProductPrice());
            cm.insertCart(cart);
            //必须调用微服务么? 真正的拆分结构,除了功能拆分,数据库表格
            //也是拆分的,cart微服务未必有能力访问t_product
        }
    }

    public void updateCartNum(Cart cart) {
        cm.updateCartNum(cart);
    }

    public void deleteCart(Cart cart) {
        cm.deleteCart(cart);
    }
}
