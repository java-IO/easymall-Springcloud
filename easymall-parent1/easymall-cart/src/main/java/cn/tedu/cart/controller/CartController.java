package cn.tedu.cart.controller;

import cn.tedu.cart.service.CartService;
import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cart/manage")
public class CartController {
    @Autowired
    private CartService cs;
    //查询我的购物车
    @RequestMapping("query")
    public List<Cart> queryMyCart(String userId){
        return cs.queryMyCart(userId);
    }
    //新增购物车
    @RequestMapping("save")
    public SysResult addCart(Cart cart){
        //userId productId num
        try{
            cs.addCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"",null);
        }
    }
    //更新购物车num数量
    @RequestMapping("update")
    public SysResult updateCartNum(Cart cart){
        try{
            cs.updateCartNum(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"",null);
        }
    }
    @RequestMapping("delete")
    public SysResult deleteCart(Cart cart){
        try{
            cs.deleteCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"",null);
        }
    }
}
